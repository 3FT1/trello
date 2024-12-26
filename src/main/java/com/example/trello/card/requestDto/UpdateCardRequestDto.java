package com.example.trello.card.requestDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCardRequestDto {

    Long CardListId;

    String title;

    String description;

    LocalDateTime startAt;

    LocalDateTime endAt;

}
