package com.example.trello.board.dto;

import lombok.Getter;

@Getter
public class viewAllBoardRequestDto {
    private Long workspaceId;

    public viewAllBoardRequestDto(Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}
