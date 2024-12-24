package com.example.trello.workspace_user;

import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.WorkSpaceRepository;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_user.dto.WorkspaceUserResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.trello.workspace_user.WorkspaceUserRole.READ_ONLY;

@Service
@RequiredArgsConstructor
public class WorkspaceUserService {

    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final UserRepository userRepository;

    @Transactional
    public WorkspaceUserResponseDto inviteWorkspaceUser(Long workspaceId, String email) {
        User findUser = userRepository.findByEmailOrElseThrow(email);
        Workspace findWorkspace = workSpaceRepository.findByIdOrElseThrow(workspaceId);

        WorkspaceUser workspaceUser = WorkspaceUser.builder()
                .user(findUser)
                .workspace(findWorkspace)
                .role(READ_ONLY)
                .build();

        workspaceUserRepository.save(workspaceUser);

        return WorkspaceUserResponseDto.toDto(workspaceUser);
    }

    @Transactional
    public String updateWorkspaceUserRole(Long workspaceId, Long workspaceUserId , WorkspaceUserRole role) {
        WorkspaceUser findWorkspaceUser = workspaceUserRepository.findByIdOrElseThrow(workspaceUserId);

        findWorkspaceUser.updateRole(role);

        return "권한이 " + role + "로 변경되었습니다.";
    }
}
