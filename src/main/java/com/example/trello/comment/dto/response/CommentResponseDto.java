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

    private Long workSpaceMemberId;

    public CommentResponseDto(Long commentId, String content, Long cardId, Long userId, Long workSpaceMemberId) {
        this.commentId = commentId;
        this.content = content;
        this.cardId = cardId;
        this.userId = userId;
        this.workSpaceMemberId = workSpaceMemberId;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCard().getId(),
                comment.getUser().getId(),
                comment.getWorkspaceMember().getId());
    }
}
