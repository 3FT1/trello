package com.example.trello.card.requestDto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CardRequestDto {

    @Column(nullable = false)
    Long CardListId;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;


}
