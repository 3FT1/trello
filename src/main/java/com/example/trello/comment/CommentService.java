package com.example.trello.comment;

import com.example.trello.card.Card;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.comment.dto.request.CommentRequestDto;
import com.example.trello.comment.dto.request.UpdateCommentRequestDto;
import com.example.trello.comment.dto.response.CommentResponseDto;
import com.example.trello.common.exception.CommentErrorCoed;
import com.example.trello.common.exception.CommentException;
import com.example.trello.common.exception.WorkspaceMemberErrorCode;
import com.example.trello.common.exception.WorkspaceMemberException;
import com.example.trello.config.auth.UserDetailsImpl;
import com.example.trello.notification.Notification;
import com.example.trello.notification.NotificationService;
import com.example.trello.user.User;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import com.example.trello.workspace_member.WorkspaceMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.trello.notification.NotificationType.CREATE_COMMENT;
import static com.example.trello.workspace_member.WorkspaceMemberRole.WORKSPACE;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceMemberService workspaceMemberService;
    private final NotificationService notificationService;
    private final WorkSpaceRepository workSpaceRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Card card = cardRepository.findByIdOrElseThrow(requestDto.getCardId());

        Long workSpaceId = card.getCardList().getBoard().getWorkspace().getId();

        WorkspaceMember workspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(userDetails.getUser().getId(), workSpaceId);

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workspaceMember.getId(), card.getCardList().getBoard().getWorkspace().getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .card(card)
                .user(userDetails.getUser())
                .workspaceMember(workspaceMember)
                .build();

        commentRepository.save(comment);

        Workspace workSpace = workSpaceRepository.findByIdOrElseThrow(workSpaceId);

        notificationService.sendSlack(CREATE_COMMENT, workSpace);
        return CommentResponseDto.toDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        Long workSpaceId = comment.getCard().getCardList().getBoard().getWorkspace().getId();

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workSpaceId, workSpaceId)) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

        if (!userDetails.getUser().getId().equals(comment.getWorkspaceMember().getUser().getId())) {
            throw new CommentException(CommentErrorCoed.CANNOT_BE_MODIFIED);
        }

        comment.updateComment(requestDto.getContent());

        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);

    }

    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        Long workSpaceId = comment.getCard().getCardList().getBoard().getWorkspace().getId();

        if (!workspaceMemberRepository.existsByUserIdAndWorkspaceId(workSpaceId, workSpaceId)) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER);
        }

        workspaceMemberService.CheckReadRole(userDetails.getUser().getId(), workSpaceId);

        if (!userDetails.getUser().getId().equals(comment.getWorkspaceMember().getUser().getId())) {
            throw new CommentException(CommentErrorCoed.CANNOT_BE_MODIFIED);
        }

        commentRepository.delete(comment);
    }
}
