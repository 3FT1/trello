package com.example.trello.comment.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponseDto {

    private Long commentId;

    private String content;

    private String nikeName;

    private Long cardId;

    private Long userId;

    public CommentResponseDto(Long commentId, String content, String  nikeName, Long cardId, Long userId) {
        this.commentId = commentId;
        this.content = content;
        this.nikeName = nikeName;
        this.cardId = cardId;
        this.userId = userId;
    }
}
