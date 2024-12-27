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

    private Long workSpaceMemberId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endAt;


    public CardResponseDto(Long cardListId, Long cardId, String title, String description, Long workSpaceMemberId, LocalDate startAt, LocalDate endAt) {
        this.cardListId = cardListId;
        this.cardId = cardId;
        this.title = title;
        this.description = description;
        this.workSpaceMemberId = workSpaceMemberId;
        this.startAt = startAt;
        this.endAt =endAt;
    }


    public static CardResponseDto toDto(Card card) {
        return new CardResponseDto(card.getCardList().getId(),
        card.getId(),
        card.getTitle(),
        card.getDescription(),
        card.getWorkspaceMember().getId(),
        card.getStartAt(),
        card.getEndAt());
    }
}
