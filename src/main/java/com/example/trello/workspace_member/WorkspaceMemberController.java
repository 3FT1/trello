package com.example.trello.workspace_member;

import com.example.trello.workspace_member.dto.UpdateWorkspaceMemberRoleDto;
import com.example.trello.workspace_member.dto.WorkspaceMemberRequestDto;
import com.example.trello.workspace_member.dto.WorkspaceMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}")
public class WorkspaceMemberController {

    private final WorkspaceMemberService workspaceMemberService;

    @PostMapping("/member-invite")
    public ResponseEntity<WorkspaceMemberResponseDto> inviteWorkspaceUser(@PathVariable Long workspaceId, @RequestBody WorkspaceMemberRequestDto dto, @SessionAttribute("id") Long loginUserId) {
        WorkspaceMemberResponseDto workspaceMemberResponseDto = workspaceMemberService.inviteWorkspaceMember(workspaceId, dto.getEmail(), loginUserId);
        return new ResponseEntity<>(workspaceMemberResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/member-role")
    public String updateWorkspaceUserRole(@PathVariable Long workspaceId, @RequestBody UpdateWorkspaceMemberRoleDto dto, @SessionAttribute("id") Long loginUserId) {
        workspaceMemberService.updateWorkspaceMemberRole(workspaceId, dto.getWorkspaceMemberId(), dto.getRole(), loginUserId);
        return "권한이 " + dto.getRole() + "로 변경되었습니다.";
    }
}
