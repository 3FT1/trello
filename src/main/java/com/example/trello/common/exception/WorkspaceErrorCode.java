package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@Getter
public enum WorkspaceErrorCode {
    ONLY_ADMIN_CAN_CREATE_WORKSPACE("ADMIN 역할의 USER만 워크스페이스를 생성할 수 있습니다.", FORBIDDEN),
    ONLY_ADMIN_CAN_UPDATE_MEMBER_ROLE_TO_WORKSPACE("ADMIN 역할의 USER만 다른 MEMBER의 역할을 워크스페이스로 수정할 수 있습니다.", FORBIDDEN),
    ONLY_WORKSPACE_ROLE_CAN_HANDLE_WORKSPACE("WORKSPACE 역할의 MEMBER만 워크스페이스를 관리할 수 있습니다.", FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;
}
