package com.example.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkspaceException extends RuntimeException {
    private WorkspaceErrorCode workspaceErrorCode;
}
