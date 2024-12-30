package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommentErrorCode {

    CANNOT_BE_MODIFIED("댓글 수정은 작성자만 가능합니다", HttpStatus.FORBIDDEN);

    private final String message;
    private final HttpStatus httpStatus;
}
