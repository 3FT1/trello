package com.example.trello.card.responsedto;

import com.example.trello.cardlist.CardList;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateCardResponseDto {

    private CardList cardList;

    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;

    public UpdateCardResponseDto(CardList cardListId, String title, String description, LocalDate startAt, LocalDate endAt) {
        this.cardList = cardListId;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
