package com.example.trello.workspace.dto;

import com.example.trello.workspace.Workspace;
import lombok.Getter;

@Getter
public class WorkspaceResponseDto {
    private Long workspaceId;
    private String title;
    private String description;
    private String slackUrl;

    public WorkspaceResponseDto(Long workspaceId, String title, String description, String slackUrl) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.slackUrl = slackUrl;
    }

    public static WorkspaceResponseDto toDto(Workspace workspace) {
        return new WorkspaceResponseDto(workspace.getId(), workspace.getTitle(), workspace.getDescription(), workspace.getSlackUrl());
    }
}
