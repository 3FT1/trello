package com.example.trello.card.responsedto;

import com.example.trello.cardlist.CardList;

import java.time.LocalDateTime;

public class UpdateCardResponseDto {

    CardList cardList;

    String title;

    String description;

    LocalDateTime startAt;

    LocalDateTime endAt;

    public UpdateCardResponseDto(CardList cardList, String title, String description, LocalDateTime startAt, LocalDateTime endAt) {
        this.cardList = cardList;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
