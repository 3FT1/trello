package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@Getter
public enum WorkspaceMemberErrorCode {
    IS_NOT_WORKSPACEMEMBER("WORKSPACE의 MEMBER가 아닙니다.", FORBIDDEN),
    ONLY_WORKSPACE_ROLE_CAN_INVITE("WORKSPACE 역할의 MEMBER만 초대가 가능합니다.", FORBIDDEN),
    ONLY_WORKSPACE_ROLE_CAN_UPDATE_MEMBER_ROLE("WORKSPACE 역할의 MEMBER만 다른 유저의 권한을 (BOARD, READ_ONLY)로 변경이 가능합니다.", FORBIDDEN),
    ALREADY_MEMBER("이미 워크스페이스의 멤버입니다.", BAD_REQUEST),
    CAN_NOT_FIND_WORKSPACEMEMBER_WITH_WORKSPACEMEMBER_ID("WORKSPACEMEMBER 를 찾을 수 없습니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
