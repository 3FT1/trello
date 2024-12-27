package com.example.trello.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {
    @NotBlank(message = "title 은 Null 일 수 없습니다.")
    @Size(min = 1, max = 50, message = "title 크기는 1에서 50사이여야합니다.")
    private String title;

    private String color;

    private String image;

    public UpdateBoardRequestDto(String title, String color, String image) {
        this.title = title;
        this.color = color;
        this.image = image;
    }
}
