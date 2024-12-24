package com.example.trello.workspace_user.dto;

import com.example.trello.workspace_user.WorkspaceUser;
import com.example.trello.workspace_user.WorkspaceUserRole;
import lombok.Getter;

@Getter
public class WorkspaceUserResponseDto {
    private Long userId;
    private Long workspaceId;
    private WorkspaceUserRole role;

    public WorkspaceUserResponseDto(Long workspaceId, Long userId, WorkspaceUserRole role) {
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.role = role;
    }

    public static WorkspaceUserResponseDto toDto(WorkspaceUser workspaceUser) {
        return new WorkspaceUserResponseDto(workspaceUser.getUser().getId(), workspaceUser.getWorkspace().getId(), workspaceUser.getRole());
    }
}
