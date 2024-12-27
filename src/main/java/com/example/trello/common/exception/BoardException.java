package com.example.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import okhttp3.internal.http2.ErrorCode;

@AllArgsConstructor
@Getter
public class BoardException extends RuntimeException {
    private BoardErrorCode boardErrorCode;
}
