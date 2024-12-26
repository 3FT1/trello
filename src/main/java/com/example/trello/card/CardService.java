package com.example.trello.card;

import com.example.trello.board.Board;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.workspace.Workspace;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;

    // 카드 생성
    public CardResponseDto createdCardService(CardRequestDto requestDto) {

        CardList cardList = findCardListByCardListId(requestDto.getCardListId());

        Card card = new Card(requestDto.getTitle(), requestDto.getDescription(), cardList, requestDto.getStartAt(), requestDto.getEndAt());
        cardRepository.save(card);

        return new CardResponseDto(cardList.getId(), card.getId(), requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());
    }

    //카드 업데이트
    public CardResponseDto updateCardService(Long cardId, UpdateCardRequestDto requestDto) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        CardList cardList = cardListRepository.findByIdOrElseThrow(requestDto.getCardListId());

        card.updateCard(cardList, requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());

        cardRepository.save(card);

        return new CardResponseDto(cardList.getId(), card.getId(), requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());
    }

    // 카드 삭제
    @PostMapping("/{cardsId}")
    public void deleteCardService(Long cardId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);
        cardRepository.delete(card);
    }

    // 카드 단건 조회
    public CardResponseDto findCardById(Long cardsId) {
        Card card = cardRepository.findByIdOrElseThrow(cardsId);
        return new CardResponseDto(card.getCardList().getId(), card.getId(), card.getTitle(), card.getDescription(), card.getStartAt(),card.getEndAt());
    }



    // 카드 리스트id로 카드 리스트 찾는 메소드
    public CardList findCardListByCardListId(Long id) {
        return cardListRepository.findByIdOrElseThrow(id);
    }

}
