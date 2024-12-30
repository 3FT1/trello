package com.example.trello.board.dto;

import com.example.trello.board.Board;
import com.example.trello.board.BoardColor;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private BoardColor color;
    private String image;

    public BoardResponseDto(Long id, String title, BoardColor color, String image) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.image = image;
    }

    public static BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getColor(), board.getImage());
    }
}
