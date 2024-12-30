package com.example.trello.comment;

import com.example.trello.card.Card;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.comment.dto.request.CommentRequestDto;
import com.example.trello.comment.dto.request.UpdateCommentRequestDto;
import com.example.trello.comment.dto.response.CommentResponseDto;
import com.example.trello.common.exception.WorkspaceMemberErrorCode;
import com.example.trello.common.exception.WorkspaceMemberException;
import com.example.trello.config.auth.UserDetailsImpl;
import com.example.trello.user.User;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.trello.workspace_member.WorkspaceMemberRole.WORKSPACE;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;


    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Card card = cardRepository.findByIdOrElseThrow(requestDto.getCardId());

        Long workspaceId = card.getCardList().getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), card.getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }


        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("댓글을 생성할 권한이 없습니다.");
        }

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .card(card)
                .user(userDetails.getUser())
                .workspaceMember(workspaceMember)
                .build();

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        Long workspaceId = comment.getCard().getCardList().getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), comment.getCard().getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }


        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("댓글을 수정할 권한이 없습니다.");
        }

        if (!userDetails.getUser().getId().equals(comment.getWorkspaceMember().getUser().getId())) {
            throw new RuntimeException();
        }

        comment.updateComment(requestDto.getContent());

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);

    }

    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        Long workspaceId = comment.getCard().getCardList().getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workspaceId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), comment.getCard().getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        if (workspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("댓글을 삭제할 권한이 없습니다.");
        }

        if (!userDetails.getUser().getId().equals(comment.getWorkspaceMember().getUser().getId())) {
            throw new RuntimeException();
        }

        commentRepository.delete(comment);
    }
}
