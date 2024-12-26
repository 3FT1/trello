package com.example.trello.cardlist.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCardListResponseDto {

    private Long id;

    private Integer sequence;
}
