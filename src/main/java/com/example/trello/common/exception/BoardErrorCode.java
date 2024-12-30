package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum BoardErrorCode {
    CAN_NOT_FIND_BOARD_WITH_BOARD_ID("보드를 찾을 수 없습니다.", BAD_REQUEST),
    IS_NOT_ALLOWED_FILE_EXTENSION("이미지 파일(확장자)만 업로드가 가능합니다.", BAD_REQUEST),
    FAILED_IMAGE_UPLOAD("이미지 업로드에 실패했습니다.", BAD_REQUEST),
    FAILED_IMAGE_DELETE("이미지 삭제에 실패했습니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
