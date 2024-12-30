package com.example.trello.workspace;

import com.example.trello.common.exception.WorkspaceErrorCode;
import com.example.trello.common.exception.WorkspaceException;
import com.example.trello.user.User;
import com.example.trello.user.UserRepository;
import com.example.trello.workspace.dto.UpdateWorkspaceRequestDto;
import com.example.trello.workspace.dto.WorkspaceRequestDto;
import com.example.trello.workspace.dto.WorkspaceResponseDto;


import com.example.trello.workspace_member.WorkspaceMember;
import com.example.trello.workspace_member.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static com.example.trello.user.enums.Role.ADMIN;
import static com.example.trello.workspace_member.WorkspaceMemberRole.WORKSPACE;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional
    public WorkspaceResponseDto createWorkspace(WorkspaceRequestDto dto, Long loginUserId) {
        User loginUser = userRepository.findByIdOrElseThrow(loginUserId);

        if (loginUser.getRole() != ADMIN) {
            throw new WorkspaceException(WorkspaceErrorCode.ONLY_ADMIN_CAN_CREATE_WORKSPACE);
        }

        Workspace workspace = Workspace.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .slackUrl(dto.getSlackUrl())
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
        List<WorkspaceMember> WorkspaceMemberListByUser = workspaceMemberRepository.findByUserId(loginUserId);

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
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, workspaceId);

        Workspace workspace = findWorkspaceMember.getWorkspace();

        return WorkspaceResponseDto.toDto(workspace);
    }

    @Transactional
    public WorkspaceResponseDto updateWorkspace(Long workspaceId, UpdateWorkspaceRequestDto dto, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, workspaceId);

        if (findWorkspaceMember.getRole() != WORKSPACE) {
            throw new WorkspaceException(WorkspaceErrorCode.ONLY_WORKSPACE_ROLE_CAN_HANDLE_WORKSPACE);
        }

        Workspace workspace = findWorkspaceMember.getWorkspace();

        workspace.updateWorkspace(dto.getTitle(), dto.getDescription(), dto.getSlackUrl());

        return WorkspaceResponseDto.toDto(workspace);
    }

    @Transactional
    public void deleteWorkspace(Long workspaceId, Long loginUserId) {
        WorkspaceMember findWorkspaceMember = workspaceMemberRepository.findByUserIdAndWorkspaceIdOrElseThrow(loginUserId, workspaceId);

        if (findWorkspaceMember.getRole() != WORKSPACE) {
            throw new WorkspaceException(WorkspaceErrorCode.ONLY_WORKSPACE_ROLE_CAN_HANDLE_WORKSPACE);
        }

        Workspace workspace = findWorkspaceMember.getWorkspace();

        workSpaceRepository.delete(workspace);
    }
}
