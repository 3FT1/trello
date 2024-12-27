package com.example.trello.board.dto;

import com.example.trello.board.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String color;
    private String image;

    public BoardResponseDto(Long id, String title, String color, String image) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.image = image;
    }

    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getColor(), board.getImage());
    }
}
