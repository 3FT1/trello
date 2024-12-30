package com.example.trello.cardlist.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardListResponseDto {

    private Long id;

    private Integer sequence;
}
