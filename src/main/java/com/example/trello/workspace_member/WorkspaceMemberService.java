package com.example.trello.workspace_member;

import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace.Workspace;
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
    public WorkspaceMemberResponseDto inviteWorkspaceMember(Long workspaceId, String email, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdOrElseThrow(loginUserId, workspaceId);

        if (findWorkspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("멤버를 초대할 권한이 없습니다.");
        }

        User findUser = userRepository.findByEmailOrElseThrow(email);
        Workspace workspace = findWorkspaceMember.getWorkspace();

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .user(findUser)
                .workspace(workspace)
                .role(READ_ONLY)
                .build();

        workspaceMemberRepository.save(workspaceMember);

        return WorkspaceMemberResponseDto.toDto(workspaceMember);
    }

    @Transactional
    public void updateWorkspaceMemberRole(Long workspaceId, Long workspaceMemberId , WorkspaceMemberRole role, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdOrElseThrow(loginUserId, workspaceId);

        if (role == WORKSPACE && findWorkspaceMember.getUser().getRole() != ADMIN) {
            throw new RuntimeException("WORKSPACE 역할은 ADMIN 만 부여할 수 있습니다.");
        }

        if (findWorkspaceMember.getRole() != WORKSPACE) {
            throw new RuntimeException("멤버 역할을 수정할 권한이 없습니다.");
        }

        WorkspaceMember roleUpdatedWorkspaceMember = workspaceMemberRepository.findByIdOrElseThrow(workspaceMemberId);

        roleUpdatedWorkspaceMember.updateRole(role);
        log.info("{}의 역할이 {}로 변경되었습니다.", roleUpdatedWorkspaceMember.getUser().getNickname(), role);
    }
}
