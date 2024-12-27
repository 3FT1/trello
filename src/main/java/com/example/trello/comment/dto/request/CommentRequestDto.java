package com.example.trello.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String content;

    private Long cardId;

    public CommentRequestDto(String content, Long cardId) {
        this.content = content;
        this.cardId = cardId;
    }
}
