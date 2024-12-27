package com.example.trello.comment.dto.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {

    private String content;

    public UpdateCommentRequestDto (String content) {
        this.content = content;
    }
}
