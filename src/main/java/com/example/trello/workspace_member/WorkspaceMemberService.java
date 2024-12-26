package com.example.trello.workspace_member;

import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.dto.WorkspaceMemberResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.trello.workspace_member.WorkspaceMemberRole.READ_ONLY;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final UserRepository userRepository;

    @Transactional
    public WorkspaceMemberResponseDto inviteWorkspaceMember(Long workspaceId, String email) {
        User findUser = userRepository.findByEmailOrElseThrow(email);
        Workspace findWorkspace = workSpaceRepository.findByIdOrElseThrow(workspaceId);

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .user(findUser)
                .workspace(findWorkspace)
                .role(READ_ONLY)
                .build();

        workspaceMemberRepository.save(workspaceMember);

        return WorkspaceMemberResponseDto.toDto(workspaceMember);
    }

    @Transactional
    public String updateWorkspaceMemberRole(Long workspaceId, Long workspaceMemberId , WorkspaceMemberRole role) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByIdOrElseThrow(workspaceMemberId);

        findWorkspaceMember.updateRole(role);

        return "권한이 " + role + "로 변경되었습니다.";
    }
}
