package com.example.trello.workspace;

import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.dto.WorkspaceRequestDto;
import com.example.trello.workspace.dto.WorkspaceResponseDto;


import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static com.example.trello.user.enums.Role.ADMIN;
import static com.example.trello.workspace_member.WorkspaceMemberRole.READ_ONLY;
import static com.example.trello.workspace_member.WorkspaceMemberRole.WORKSPACE;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional
    public WorkspaceResponseDto createWorkspace(String title, String description, Long loginUserId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginUserId);

        if (loginUser.getRole() != ADMIN) {
            throw new RuntimeException("권한이 ADMIN 인 유저만 워크스페이스 생성이 가능합니다.");
        }

        Workspace workspace = Workspace.builder()
                .title(title)
                .description(description)
                .user(loginUser)
                .build();

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .user(loginUser)
                .workspace(workspace)
                .role(WORKSPACE)
                .build();

        workSpaceRepository.save(workspace);
        workspaceMemberRepository.save(workspaceMember);

        return WorkspaceResponseDto.toDto(workspace);
    }

    @Transactional(readOnly = true)
    public List<WorkspaceResponseDto> viewAllWorkspace(Long loginUserId) {
        User findUser = userRepository.findByIdOrElseThrow(loginUserId);
        List<WorkspaceMember> WorkspaceMemberListByUser = workspaceMemberRepository.findByUser(findUser);

        List<Workspace> workspaceList = new ArrayList<>();
        for (WorkspaceMember workspaceMember : WorkspaceMemberListByUser) {
            workspaceList.add(workspaceMember.getWorkspace());
        }

        return workspaceList
                .stream()
                .map(WorkspaceResponseDto::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkspaceResponseDto viewWorkspace(Long workspaceId, Long loginUserId) {
        Workspace findWorkspace = workSpaceRepository.findByIdOrElseThrow(workspaceId);

        if (!loginUserId.equals(findWorkspace.getUser().getId())) {
            throw new RuntimeException("조회할 권한이 없는 워크스페이스입니다.");
        }

        return WorkspaceResponseDto.toDto(findWorkspace);
    }

    @Transactional
    public WorkspaceResponseDto updateWorkspace(Long workspaceId, String title, String description) {
        Workspace findWorkspace = workSpaceRepository.findByIdOrElseThrow(workspaceId);

        findWorkspace.updateWorkspace(title, description);

        return WorkspaceResponseDto.toDto(findWorkspace);
    }

    @Transactional
    public void deleteWorkspace(Long workspaceId) {
        Workspace findWorkspace = workSpaceRepository.findByIdOrElseThrow(workspaceId);
        workSpaceRepository.delete(findWorkspace);
    }
}
