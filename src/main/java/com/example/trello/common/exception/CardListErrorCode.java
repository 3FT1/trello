package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CardListErrorCode {
    CARD_LIST_NOT_FOUND("리스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_SEQUENCE("지정한 순서로 변경할수 없습니다", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
