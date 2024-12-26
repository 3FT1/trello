package com.example.trello.card;

import com.example.trello.board.Board;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.card.responsedto.UpdateCardResponseDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.CardListRepository;
import com.example.trello.workspace.Workspace;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;

    // 카드 생성
    public CardResponseDto createdCardService(CardRequestDto requestDto, HttpServletRequest servletRequest) {

        CardList cardList = findCardListByCardListId(requestDto.getCardListId());

        Card card = new Card(requestDto.getTitle(), requestDto.getDescription(), cardList);
        cardRepository.save(card);

        return new CardResponseDto(cardList.getId(), card.getId(), requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());
    }

//    //카드 업데이트
//    public CardResponseDto updateCardService(Long cardId, UpdateCardRequestDto requestDto, HttpServletRequest servletRequest) {
//        Card card = cardRepository.findByIdOrElseThrow(cardId);
//
//        CardList cardList = cardListRepository.findByIdOrElseThrow(requestDto.getCardListId());
//
//        UpdateCardResponseDto updateCardResponseDto = new UpdateCardResponseDto(cardList, requestDto.getTitle(), requestDto.getDescription(), requestDto.getStartAt(), requestDto.getEndAt());
//
//
//        card.updateCard(requestDto);
//        cardRepository.save(card);
//
//        return new CardResponseDto(cardList.getId(), card.getId(), requestDto.getTitle(), requestDto.getDescription());
//    }

    // 카드 삭제(일단 hard delete 추후의 수정가능)
    public void deleteCardService(Long cardId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);
        cardRepository.delete(card);
    }


    // 카드 리스트id로 카드 리스트 찾는 메소드
    public CardList findCardListByCardListId(Long id) {
        return cardListRepository.findByIdOrElseThrow(id);
    }

}
