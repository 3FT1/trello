package com.example.trello.board.dto;

import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {
    private String title;
    private String color;
    private String image;

    public UpdateBoardRequestDto(String title, String color, String image) {
        this.title = title;
        this.color = color;
        this.image = image;
    }
}
