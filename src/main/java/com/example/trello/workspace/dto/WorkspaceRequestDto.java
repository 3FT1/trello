package com.example.trello.workspace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class WorkspaceRequestDto {
    @NotBlank(message = "title 은 Null 일 수 없습니다.")
    @Size(min = 1, max = 50, message = "title 크기는 1에서 50사이여야합니다.")
    private String title;

    @NotBlank(message = "description 은 Null 일 수 없습니다.")
    @Size(min = 1, max = 255, message = "title 크기는 1에서 255사이여야합니다.")
    private String description;

    private String slackUrl;

    public WorkspaceRequestDto(String title, String description, String slackUrl) {
        this.title = title;
        this.description = description;
        this.slackUrl = slackUrl;
    }
}
