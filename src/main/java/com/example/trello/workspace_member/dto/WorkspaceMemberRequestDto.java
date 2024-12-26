package com.example.trello.workspace_member.dto;

import lombok.Getter;

@Getter
public class WorkspaceMemberRequestDto {
    private String email;

    public WorkspaceMemberRequestDto(String email) {
        this.email = email;
    }
}
