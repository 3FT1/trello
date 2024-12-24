package com.example.trello.workspace_user.dto;

import lombok.Getter;

@Getter
public class WorkspaceUserRequestDto {
    private String email;

    public WorkspaceUserRequestDto(String email) {
        this.email = email;
    }
}
