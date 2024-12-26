package com.example.trello.workspace_member.dto;

import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRole;
import lombok.Getter;

@Getter
public class WorkspaceMemberResponseDto {
    private Long userId;
    private Long workspaceId;
    private WorkspaceMemberRole role;

    public WorkspaceMemberResponseDto(Long workspaceId, Long userId, WorkspaceMemberRole role) {
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.role = role;
    }

    public static WorkspaceMemberResponseDto toDto(WorkspaceMember workspaceMember) {
        return new WorkspaceMemberResponseDto(workspaceMember.getUser().getId(), workspaceMember.getWorkspace().getId(), workspaceMember.getRole());
    }
}
