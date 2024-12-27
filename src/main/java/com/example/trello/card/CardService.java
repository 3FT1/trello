package com.example.trello.card;

import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.cardrepository.CardRepositoryCustomImpl;
import com.example.trello.card.responsedto.CardPageResponseDto;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.config.auth.UserDetailsImpl;
import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.trello.workspace_member.WorkspaceMemberRole.READ_ONLY;
import static com.example.trello.workspace_member.WorkspaceMemberRole.WORKSPACE;

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
    @Transactional
    public CardResponseDto createdCardService(CardRequestDto requestDto, UserDetailsImpl userDetails) {

        CardList cardList = cardListRepository.findByIdOrElseThrow(requestDto.getCardListId());

        Long workspaceId = cardList.getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("카드를 생성할 권한이 없습니다.");
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
    @Transactional
    public CardResponseDto updateCardService(Long cardId, UpdateCardRequestDto requestDto, UserDetailsImpl userDetails) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        CardList cardList = cardListRepository.findByIdOrElseThrow(requestDto.getCardListId());

        Long workspaceId = cardList.getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("카드를 수정할 권한이 없습니다.");
        }

        card.updateCard(cardList, requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());

        cardRepository.save(card);

        return CardResponseDto.toDto(card);
    }

    // 카드 삭제
    @Transactional
    public void deleteCardService(Long cardId, UserDetailsImpl userDetails) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        Long workspaceId = card.getCardList().getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("카드를 삭제할 권한이 없습니다.");
        }

        cardRepository.delete(card);
    }

    // 카드 단건 조회
    @Transactional
    public CardResponseDto findCardById(Long cardId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);
        return CardResponseDto.toDto(card);
    }

    // 카드 다건 조회(조건 O)
    @Transactional
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
