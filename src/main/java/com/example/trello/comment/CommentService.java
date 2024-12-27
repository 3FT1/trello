package com.example.trello.comment;

import com.example.trello.card.Card;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.comment.dto.request.CommentRequestDto;
import com.example.trello.comment.dto.request.UpdateCommentRequestDto;
import com.example.trello.comment.dto.response.CommentResponseDto;
import com.example.trello.user.User;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;


    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest servletRequest) {

        User user = (User) servletRequest.getSession().getAttribute("id");

        Card card = cardRepository.findByIdOrElseThrow(requestDto.getCardId());

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByIdOrElseThrow(requestDto.getWorkSpaceMemberId());

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .card(card)
                .user(user)
                .workspaceMember(workspaceMember)
                .build();

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto requestDto, HttpServletRequest servletRequest) {

        User user = (User) servletRequest.getSession().getAttribute("id");

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if (!user.getId().equals(comment.getWorkspaceMember().getUser().getId())) {
            throw new RuntimeException();
        }

        comment.updateComment(requestDto.getContent());

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);

    }

    public void deleteComment(Long commentId, HttpServletRequest servletRequest) {

        User user = (User) servletRequest.getSession().getAttribute("id");

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if (!user.getId().equals(comment.getWorkspaceMember().getUser().getId())) {
            throw new RuntimeException();
        }

        commentRepository.delete(comment);
    }
}
