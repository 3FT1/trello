package com.example.trello.card.responsedto;

import com.example.trello.card.Card;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class CardResponseDto {


    private Long cardListId;

    private Long cardId;

    private String title;

    private String description;

    private String nikeName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endAt;


    public CardResponseDto(Long cardListId, Long cardId, String title, String description, String nikeName, LocalDate startAt, LocalDate endAt) {
        this.cardListId = cardListId;
        this.cardId = cardId;
        this.title = title;
        this.description = description;
        this.nikeName = nikeName;
        this.startAt = startAt;
        this.endAt =endAt;
    }

    public CardResponseDto(Card card) {
        this.cardListId = card.getCardList().getId();
        this.cardId = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.nikeName = card.getNikeName();
        this.startAt = card.getStartAt();
        this.endAt =card.getEndAt();
    }
}
