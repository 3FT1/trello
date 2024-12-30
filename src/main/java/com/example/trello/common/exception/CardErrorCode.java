package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CardErrorCode {

    CARD_NOT_FOUND("카드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FORMAT_NOT_SUPPORTED("지원하지 않는 형식입니다", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED("파일의 크기가 5MB를 넘었습니다 파일의 크기를 줄여주세요", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
