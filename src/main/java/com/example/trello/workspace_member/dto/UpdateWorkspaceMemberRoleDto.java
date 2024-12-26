package com.example.trello.workspace_member.dto;

import com.example.trello.workspace_member.WorkspaceMemberRole;
import lombok.Getter;

@Getter
public class UpdateWorkspaceMemberRoleDto {
    private Long workspaceMemberId;
    private WorkspaceMemberRole role;

    public UpdateWorkspaceMemberRoleDto(Long workspaceMemberId, WorkspaceMemberRole role) {
        this.workspaceMemberId = workspaceMemberId;
        this.role = role;
    }
}
