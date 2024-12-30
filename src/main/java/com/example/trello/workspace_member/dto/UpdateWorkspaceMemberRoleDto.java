package com.example.trello.workspace_member.dto;

import com.example.trello.workspace_member.WorkspaceMemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateWorkspaceMemberRoleDto {
    @NotNull
    private Long workspaceMemberId;

    @NotNull
    private WorkspaceMemberRole role;

    public UpdateWorkspaceMemberRoleDto(Long workspaceMemberId, WorkspaceMemberRole role) {
        this.workspaceMemberId = workspaceMemberId;
        this.role = role;
    }
}
