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

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    // todo : Admin 권한을 가진 유저만 생성가능하게
    @Transactional
    public WorkspaceResponseDto createWorkspace(String title, String description) {
        Workspace workspace = Workspace.builder()
                .title(title)
                .description(description)
                .build();

        workSpaceRepository.save(workspace);

        return WorkspaceResponseDto.toDto(workspace);
    }
}