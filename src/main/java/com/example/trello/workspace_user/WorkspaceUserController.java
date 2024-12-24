package com.example.trello.workspace_user;

import com.example.trello.workspace_user.dto.UpdateWorkspaceUserRoleDto;
import com.example.trello.workspace_user.dto.WorkspaceUserRequestDto;
import com.example.trello.workspace_user.dto.WorkspaceUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}")
public class WorkspaceUserController {

    private final WorkspaceUserService workspaceUserService;

    @PostMapping("/member-invite")
    public ResponseEntity<WorkspaceUserResponseDto> inviteWorkspaceUser(@PathVariable Long workspaceId, @RequestBody WorkspaceUserRequestDto dto) {
        WorkspaceUserResponseDto workspaceUserResponseDto = workspaceUserService.inviteWorkspaceUser(workspaceId, dto.getEmail());
        return new ResponseEntity<>(workspaceUserResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/member-role")
    public String updateWorkspaceUserRole(@PathVariable Long workspaceId, @RequestBody UpdateWorkspaceUserRoleDto dto) {
        return workspaceUserService.updateWorkspaceUserRole(workspaceId, dto.getWorkspaceUserId(), dto.getRole());
    }
}
