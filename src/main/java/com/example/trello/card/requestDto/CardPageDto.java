package com.example.trello.card.requestDto;

import com.example.trello.card.Card;
import com.example.trello.card.responsedto.CardResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CardPageDto {

    private List<CardResponseDto> cardList;
    private Long count;

    public CardPageDto(List<Card> cardList, Long count) {
        this.cardList = cardList.stream()
                .map(CardResponseDto::toDto)
                .toList();
        this.count = count;
    }
}
