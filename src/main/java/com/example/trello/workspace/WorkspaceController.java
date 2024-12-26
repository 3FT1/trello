package com.example.trello.workspace;

import com.example.trello.workspace.dto.UpdateWorkspaceRequestDto;
import com.example.trello.workspace.dto.WorkspaceRequestDto;
import com.example.trello.workspace.dto.WorkspaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceRequestDto dto, @SessionAttribute("id") Long loginUserId) {
        WorkspaceResponseDto createdWorkspaceResponseDto = workspaceService.createWorkspace(dto.getTitle(), dto.getDescription(), loginUserId);

        return new ResponseEntity<>(createdWorkspaceResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponseDto>> viewAllWorkspace(@SessionAttribute("id") Long loginUserId) {
        List<WorkspaceResponseDto> workspaceResponseDtoList = workspaceService.viewAllWorkspace(loginUserId);
        return new ResponseEntity<>(workspaceResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> viewWorkspace(@PathVariable Long workspaceId, @SessionAttribute("id") Long loginUserId) {
        WorkspaceResponseDto findWorkspaceResponseDto = workspaceService.viewWorkspace(workspaceId, loginUserId);
        return new ResponseEntity<>(findWorkspaceResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> updateWorkspace(@PathVariable Long workspaceId, @RequestBody UpdateWorkspaceRequestDto dto, @SessionAttribute("id") Long loginUserId) {
        WorkspaceResponseDto updatedWorkspaceResponseDto = workspaceService.updateWorkspace(workspaceId, dto.getTitle(), dto.getDescription(), loginUserId);
        return new ResponseEntity<>(updatedWorkspaceResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{workspaceId}")
    public String deleteWorkspace(@PathVariable Long workspaceId, @SessionAttribute("id") Long loginUserId) {
        workspaceService.deleteWorkspace(workspaceId, loginUserId);
        return "워크스페이스가 정상적으로 삭제되었습니다.";
    }
}
