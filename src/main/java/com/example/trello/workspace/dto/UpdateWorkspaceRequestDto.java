package com.example.trello.workspace.dto;

import lombok.Getter;

@Getter
public class UpdateWorkspaceRequestDto {
    private String title;
    private String description;

    public UpdateWorkspaceRequestDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
