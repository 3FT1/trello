package com.example.trello.card.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCardRequestDto {

    private Long CardListId;

    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endAt;

    public UpdateCardRequestDto(String title, Long cardListId, String description, LocalDateTime startAt, LocalDateTime endAt) {
        this.title = title;
        this.CardListId = cardListId;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
