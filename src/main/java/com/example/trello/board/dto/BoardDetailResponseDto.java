package com.example.trello.board.dto;

import com.example.trello.board.Board;
import com.example.trello.cardlist.CardList;
import com.example.trello.cardlist.dto.GetCardListResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardDetailResponseDto {
    private Long id;
    private String title;
    private String color;
    private String image;
    private List<GetCardListResponseDto> cardList;

    public BoardDetailResponseDto(Long id, String title, String color, String image, List<GetCardListResponseDto> cardList) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.image = image;
        this.cardList = cardList;
    }

    public static BoardDetailResponseDto toDto(Board board, List<GetCardListResponseDto> cardList) {
        return new BoardDetailResponseDto(board.getId(), board.getTitle(), board.getColor(), board.getImage(), cardList);
    }
}
