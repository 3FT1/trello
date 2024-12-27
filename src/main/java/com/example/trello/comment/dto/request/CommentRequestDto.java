package com.example.trello.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String content;

    private Long cardId;

    private Long workSpaceMemberId;

    public CommentRequestDto(String content, Long cardId, Long workSpaceMemberId) {
        this.content = content;
        this.cardId = cardId;
        this.workSpaceMemberId = workSpaceMemberId;
    }
}
