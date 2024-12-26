package com.example.trello.card.responsedto;

import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CardResponseDto {

    Long cardListId;

    Long cardId;

    String title;

    String description;

    LocalDateTime startAt;

    LocalDateTime endAt;


    public CardResponseDto(Long cardListId, Long cardId, String title, String description, LocalDateTime startAt, LocalDateTime endAt) {
        this.cardListId = cardListId;
        this.cardId = cardId;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt =endAt;
    }
}
