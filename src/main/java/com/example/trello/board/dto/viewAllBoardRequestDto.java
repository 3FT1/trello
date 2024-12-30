package com.example.trello.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class viewAllBoardRequestDto {
    @NotNull
    private Long workspaceId;

    public viewAllBoardRequestDto(Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}
