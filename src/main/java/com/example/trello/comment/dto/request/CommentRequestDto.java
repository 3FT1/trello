package com.example.trello.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String content;

    private Long cardId;

    private Long userId;

    public CommentRequestDto(String content, Long cardId, Long userId) {
        this.content = content;
        this.cardId = cardId;
        this.userId = userId;
    }
}
