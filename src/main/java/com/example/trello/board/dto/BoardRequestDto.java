package com.example.trello.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String color;
    private String image;
    private Long workspaceId;

    public BoardRequestDto(String title, String color, String image, Long workspaceId) {
        this.title = title;
        this.color = color;
        this.image = image;
        this.workspaceId = workspaceId;
    }
}
