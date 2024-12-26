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
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceRequestDto dto) {
        WorkspaceResponseDto createdWorkspaceResponseDto = workspaceService.createWorkspace(dto.getTitle(), dto.getDescription());

        return new ResponseEntity<>(createdWorkspaceResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponseDto>> viewAllWorkspace() {
        List<WorkspaceResponseDto> workspaceResponseDtoList = workspaceService.viewAllWorkspace();
        return new ResponseEntity<>(workspaceResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> viewWorkspace(@PathVariable Long workspaceId) {
        WorkspaceResponseDto findWorkspaceResponseDto = workspaceService.viewWorkspace(workspaceId);
        return new ResponseEntity<>(findWorkspaceResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponseDto> updateWorkspace(@PathVariable Long workspaceId, @RequestBody UpdateWorkspaceRequestDto dto) {
        WorkspaceResponseDto updatedWorkspaceResponseDto = workspaceService.updateWorkspace(workspaceId, dto.getTitle(), dto.getDescription());
        return new ResponseEntity<>(updatedWorkspaceResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long workspaceId) {
        workspaceService.deleteWorkspace(workspaceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
