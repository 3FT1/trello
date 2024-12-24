package com.example.trello.workspace_user.dto;

import com.example.trello.workspace_user.WorkspaceUserRole;
import lombok.Getter;

@Getter
public class UpdateWorkspaceUserRoleDto {
    private Long userId;
    private Long workspaceId;
    private WorkspaceUserRole role;

    public UpdateWorkspaceUserRoleDto(Long userId, Long workspaceId, WorkspaceUserRole role) {
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.role = role;
    }
}
