package com.example.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String errorCode;
    private String message;
}
