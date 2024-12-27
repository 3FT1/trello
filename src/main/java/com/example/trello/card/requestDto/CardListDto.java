package com.example.trello.card.requestDto;

import com.example.trello.card.Card;
import com.example.trello.card.responsedto.CardResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CardListDto {

    private List<CardResponseDto> cardList;
    private Long count;

    public CardListDto(List<Card> cardList, Long count) {
        this.cardList = cardList.stream()
                .map(card -> new CardResponseDto(card))
                .toList();
        this.count = count;
    }
}
