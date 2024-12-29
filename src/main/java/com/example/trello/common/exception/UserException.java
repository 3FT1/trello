package com.example.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import okhttp3.internal.http2.ErrorCode;

@RequiredArgsConstructor
@Getter
public class UserException extends RuntimeException {
  private final UserErrorCode errorCode;
}
