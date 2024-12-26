package com.example.trello.workspace.dto;

import com.example.trello.workspace.Workspace;
import lombok.Getter;

@Getter
public class WorkspaceResponseDto {
    private Long workspaceId;
    private String title;
    private String description;

    public WorkspaceResponseDto(Long workspaceId, String title, String description) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
    }

    public static WorkspaceResponseDto toDto(Workspace workspace) {
        return new WorkspaceResponseDto(workspace.getId(), workspace.getTitle(), workspace.getDescription());
    }
}
