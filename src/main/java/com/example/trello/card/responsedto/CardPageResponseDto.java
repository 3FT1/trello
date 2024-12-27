package com.example.trello.card.responsedto;

import com.example.trello.card.Card;
import lombok.Getter;

import java.util.List;

@Getter
public class CardPageResponseDto {

    private List<CardResponseDto> cardList;
    private Long count;

    public CardPageResponseDto(List<Card> cardList, Long count) {
        this.cardList = cardList.stream()
                .map(CardResponseDto::toDto)
                .toList();
        this.count = count;
    }
}
