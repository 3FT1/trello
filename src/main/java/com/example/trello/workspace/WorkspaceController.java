package com.example.trello.workspace;

import com.example.trello.config.auth.UserDetailsImpl;
import com.example.trello.workspace.dto.UpdateWorkspaceRequestDto;
import com.example.trello.workspace.dto.WorkspaceRequestDto;
import com.example.trello.workspace.dto.WorkspaceResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@Valid @RequestBody WorkspaceRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        WorkspaceResponseDto createdWorkspaceResponseDto = workspaceService.createWorkspace(dto, userDetails.getUser().getId());

        return new ResponseEntity<>(createdWorkspaceResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponseDto>> viewAllWorkspace(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<WorkspaceResponseDto> workspaceResponseDtoList = workspaceService.viewAllWorkspace(userDetails.getUser().getId());
        return new ResponseEntity<>(workspaceResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> viewWorkspace(@PathVariable Long workspaceId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        WorkspaceResponseDto findWorkspaceResponseDto = workspaceService.viewWorkspace(workspaceId, userDetails.getUser().getId());
        return new ResponseEntity<>(findWorkspaceResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> updateWorkspace(@PathVariable Long workspaceId, @Valid @RequestBody UpdateWorkspaceRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        WorkspaceResponseDto updatedWorkspaceResponseDto = workspaceService.updateWorkspace(workspaceId, dto, userDetails.getUser().getId());
        return new ResponseEntity<>(updatedWorkspaceResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<String> deleteWorkspace(@PathVariable Long workspaceId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        workspaceService.deleteWorkspace(workspaceId, userDetails.getUser().getId());
        return new ResponseEntity<>("워크스페이스가 정상적으로 삭제되었습니다.", HttpStatus.OK);
    }
}
