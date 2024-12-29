package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum BoardErrorCode {
    READ_ONLY_CANT_NOT_HANDLE_BOARD("읽기 전용 역할은 할 수 없는 작업입니다.", FORBIDDEN),
    CAN_NOT_FIND_BOARD_WITH_BOARD_ID("보드를 찾을 수 없습니다.", BAD_REQUEST);


    private final String message;
    private final HttpStatus httpStatus;
}
