package com.example.trello.workspace_user.dto;

import com.example.trello.workspace_user.WorkspaceUserRole;
import lombok.Getter;

@Getter
public class UpdateWorkspaceUserRoleDto {
    private Long workspaceUserId;
    private WorkspaceUserRole role;

    public UpdateWorkspaceUserRoleDto(Long workspaceUserId, WorkspaceUserRole role) {
        this.workspaceUserId = workspaceUserId;
        this.role = role;
    }
}
