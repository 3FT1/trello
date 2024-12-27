package com.example.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkspaceMemberException extends RuntimeException {
    private WorkspaceMemberErrorCode workspaceMemberErrorCode;
}
