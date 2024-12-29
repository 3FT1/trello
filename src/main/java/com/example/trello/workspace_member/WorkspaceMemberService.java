package com.example.trello.workspace_member;

import com.example.trello.common.exception.WorkspaceErrorCode;
import com.example.trello.common.exception.WorkspaceException;
import com.example.trello.common.exception.WorkspaceMemberErrorCode;
import com.example.trello.common.exception.WorkspaceMemberException;
import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.dto.UpdateWorkspaceMemberRoleDto;
import com.example.trello.workspace_member.dto.WorkspaceMemberRequestDto;
import com.example.trello.workspace_member.dto.WorkspaceMemberResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.trello.user.enums.Role.ADMIN;
import static com.example.trello.workspace_member.WorkspaceMemberRole.READ_ONLY;
import static com.example.trello.workspace_member.WorkspaceMemberRole.WORKSPACE;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final UserRepository userRepository;

    @Transactional
    public WorkspaceMemberResponseDto inviteWorkspaceMember(Long workspaceId, WorkspaceMemberRequestDto dto, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, workspaceId);

        if (findWorkspaceMember.getRole() != WORKSPACE) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.ONLY_WORKSPACE_ROLE_CAN_INVITE);
        }

        User findUser = userRepository.findByEmailOrElseThrow(dto.getEmail());
        Workspace workspace = findWorkspaceMember.getWorkspace();

        if (workspaceMemberRepository.existsByUserIdAndWorkspaceId(findUser.getId(), workspace.getId())) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.ALREADY_MEMBER);
        }

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .user(findUser)
                .workspace(workspace)
                .role(READ_ONLY)
                .build();

        workspaceMemberRepository.save(workspaceMember);

        return WorkspaceMemberResponseDto.toDto(workspaceMember);
    }

    @Transactional
    public void updateWorkspaceMemberRole(Long workspaceId, UpdateWorkspaceMemberRoleDto dto, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, workspaceId);

        if (dto.getRole() == WORKSPACE && findWorkspaceMember.getUser().getRole() != ADMIN) {
            throw new WorkspaceException(WorkspaceErrorCode.ONLY_ADMIN_CAN_UPDATE_MEMBER_ROLE_TO_WORKSPACE);
        }

        if (findWorkspaceMember.getRole() != WORKSPACE) {
            throw new WorkspaceMemberException(WorkspaceMemberErrorCode.ONLY_WORKSPACE_ROLE_CAN_UPDATE_MEMBER_ROLE);
        }

        WorkspaceMember roleUpdatedWorkspaceMember = workspaceMemberRepository.findByIdAndWorkspaceIdOrElseThrow(dto.getWorkspaceMemberId(), workspaceId);

        roleUpdatedWorkspaceMember.updateRole(dto.getRole());
        log.info("{}의 역할이 {}로 변경되었습니다.", roleUpdatedWorkspaceMember.getUser().getNickname(), dto.getRole());
    }
}
