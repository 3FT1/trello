package com.example.trello.cardlist.dto;

import com.example.trello.board.Board;
import com.example.trello.cardlist.CardList;
import lombok.Getter;

@Getter
public class CreateCardListRequestDto {

    private String title;

    private Integer sequence;

    private Long boardId;

    public CardList toEntity(CreateCardListRequestDto requestDto, Board board) {
        return CardList.builder()
                .title(requestDto.title)
                .sequence(requestDto.sequence)
                .board(board)
                .build();
    }

}
