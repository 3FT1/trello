package com.example.trello.comment.dto.response;

import com.example.trello.comment.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponseDto {

    private Long commentId;

    private String content;

    private Long cardId;

    private Long userId;

    public CommentResponseDto(Long commentId, String content, Long cardId, Long userId) {
        this.commentId = commentId;
        this.content = content;
        this.cardId = cardId;
        this.userId = userId;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCard().getId(),
                comment.getUser().getId());
    }
}
