package com.example.trello.card.responsedto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CardResponseDto {

    private Long cardListId;

    private Long cardId;

    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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
