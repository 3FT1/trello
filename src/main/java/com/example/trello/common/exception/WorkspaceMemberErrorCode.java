package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@Getter
public enum WorkspaceMemberErrorCode {
    IS_NOT_WORKSPACEMEMBER("WORKSPACE의 MEMBER가 아닙니다.", FORBIDDEN),
    ONLY_WORKSPACE_ROLE_CAN_INVITE("WORKSPACE 역할의 MEMBER만 초대가 가능합니다.", FORBIDDEN),
    ONLY_WORKSPACE_ROLE_CAN_UPDATE_MEMBER_ROLE("WORKSPACE 역할의 MEMBER만 다른 유저의 권한을 (BOARD, READ_ONLY)로 변경이 가능합니다.", FORBIDDEN);


    private final String message;
    private final HttpStatus httpStatus;
}
