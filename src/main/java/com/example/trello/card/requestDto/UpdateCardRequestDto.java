package com.example.trello.card.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UpdateCardRequestDto {

    private Long CardListId;

    private String title;

    private String description;

    private Long workSpaceMemberId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;

    public UpdateCardRequestDto(String title, Long cardListId, String description, Long workSpaceMemberId, LocalDate startAt, LocalDate endAt) {
        this.title = title;
        this.CardListId = cardListId;
        this.description = description;
        this.workSpaceMemberId = workSpaceMemberId;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
