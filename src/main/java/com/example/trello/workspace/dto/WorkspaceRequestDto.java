package com.example.trello.workspace.dto;

import lombok.Getter;

@Getter
public class WorkspaceRequestDto {
    private String title;
    private String description;

    public WorkspaceRequestDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
