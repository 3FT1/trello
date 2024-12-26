package com.example.trello.card.responsedto;

import jakarta.persistence.Column;
import lombok.Getter;


@Getter
public class CardResponseDto {

    @Column(updatable = false)

    Long cardListId;

    Long cardId;

    String title;

    String description;


    public CardResponseDto(Long cardListId, Long cardId, String title, String description) {
        this.cardListId = cardListId;
        this.cardId = cardId;
        this.title = title;
        this.description = description;
    }
}
