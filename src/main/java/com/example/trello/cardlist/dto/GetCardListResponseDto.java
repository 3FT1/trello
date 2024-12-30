package com.example.trello.cardlist.dto;

import com.example.trello.card.Card;
import com.example.trello.card.dto.GetCardResponseDto;
import com.example.trello.cardlist.CardList;
import lombok.Getter;

import java.util.List;

@Getter
public class GetCardListResponseDto {
    private Long id;
    private String title;
    private Integer sequence;
    private List<GetCardResponseDto> card;

    public GetCardListResponseDto(Long id, String title, Integer sequence, List<GetCardResponseDto> card) {
        this.id = id;
        this.title = title;
        this.sequence = sequence;
        this.card = card;
    }

    public static GetCardListResponseDto toDto(CardList cardList, List<GetCardResponseDto> card) {
        return new GetCardListResponseDto(cardList.getId(), cardList.getTitle(), cardList.getSequence(), card);
    }
}
