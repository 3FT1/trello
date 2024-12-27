package com.example.trello.card.dto;

import com.example.trello.card.Card;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.dto.GetCardListResponseDto;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetCardResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate startAt;
    private LocalDate endAt;

    public GetCardResponseDto(Long id, String title, String description, LocalDate startAt, LocalDate endAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static GetCardResponseDto toDto(Card card) {
        return new GetCardResponseDto(card.getId(), card.getTitle(), card.getDescription(), card.getStartAt(), card.getEndAt());
    }
}
