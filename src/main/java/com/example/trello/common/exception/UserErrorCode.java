package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode {

    NOT_FOUND_ID("요청하신 아이디값을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_EMAIL("요청하신 이메일값을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    DUPLICATED_EMAIL("중복된 이메일입니다. 다시 입력해 주세요", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

    ALREADY_DELETED("이미 탈퇴된 계정입니다.", HttpStatus.BAD_REQUEST),
    REQUIRED_LOGIN("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);


    private final String message;
    private final HttpStatus httpStatus;
}
