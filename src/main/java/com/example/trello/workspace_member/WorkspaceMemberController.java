package com.example.trello.workspace_member;

import com.example.trello.config.auth.UserDetailsImpl;
import com.example.trello.workspace_member.dto.UpdateWorkspaceMemberRoleDto;
import com.example.trello.workspace_member.dto.WorkspaceMemberRequestDto;
import com.example.trello.workspace_member.dto.WorkspaceMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}")
public class WorkspaceMemberController {

    private final WorkspaceMemberService workspaceMemberService;

    @PostMapping("/member-invite")
    public ResponseEntity<WorkspaceMemberResponseDto> inviteWorkspaceUser(@PathVariable Long workspaceId, @RequestBody WorkspaceMemberRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        WorkspaceMemberResponseDto workspaceMemberResponseDto = workspaceMemberService.inviteWorkspaceMember(workspaceId, dto, userDetails.getUser().getId());
        return new ResponseEntity<>(workspaceMemberResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/member-role")
    public String updateWorkspaceUserRole(@PathVariable Long workspaceId, @RequestBody UpdateWorkspaceMemberRoleDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        workspaceMemberService.updateWorkspaceMemberRole(workspaceId, dto, userDetails.getUser().getId());
        return "권한이 " + dto.getRole() + "로 변경되었습니다.";
    }
}
