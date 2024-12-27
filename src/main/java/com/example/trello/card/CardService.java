package com.example.trello.card;

import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.cardrepository.CardRepositoryCustomImpl;
import com.example.trello.card.responsedto.CardPageResponseDto;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.trello.workspace_member.WorkspaceMemberRole.READ_ONLY;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;
    private final CardRepositoryCustomImpl cardRepositoryCustomImpl;
    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    // 카드 생성
    public CardResponseDto createdCardService(CardRequestDto requestDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        CardList cardList = cardListRepository.findByIdOrElseThrow(requestDto.getCardListId());

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByIdOrElseThrow(requestDto.getWorkSpaceMemberId());

        if (workspaceMember.getRole().equals(READ_ONLY)) {
            throw new RuntimeException();
        }

        Card card = Card.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .workspaceMember(workspaceMember)
                .startAt(requestDto.getStartAt())
                .endAt(requestDto.getEndAt())
                .cardList(cardList)
                .build();
        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }

    //카드 업데이트
    public CardResponseDto updateCardService(Long cardId, UpdateCardRequestDto requestDto, Long userId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        CardList cardList = cardListRepository.findByIdOrElseThrow(requestDto.getCardListId());

        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        if (!user.getId().equals(card.getWorkspaceMember().getUser().getId())) {
            throw new RuntimeException();
        }

        card.updateCard(cardList, requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());

        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }

    // 카드 삭제
    public void deleteCardService(Long cardId, Long userId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        if (!user.getId().equals(card.getWorkspaceMember().getUser().getId())) {
            throw new RuntimeException();
        }
        cardRepository.delete(card);
    }

    // 카드 단건 조회
    public CardResponseDto findCardById(Long cardId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);
        return CardResponseDto.toDto(card);
    }

    // 카드 다건 조회(조건 O)
    public CardPageResponseDto searchCards(int page , Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId) {
        PageRequest pageRequest = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC, "id"));

        CardPageResponseDto cards = cardRepository.searchCard(pageRequest, cardListId, startAt, endAt, boardId);

        return cards;
    }

//    private CardListDto convertToDto(List<Card> cards) {
//        return cards.stream()
//                .map(card -> new CardResponseDto(
//                        card.getCardList().getId(),
//                        card.getId(),
//                        card.getTitle(),
//                        card.getDescription(),
//                        card.getNikeName(),
//                        card.getStartAt(),
//                        card.getEndAt()
//                ))
//                .toList();
//    }

}
