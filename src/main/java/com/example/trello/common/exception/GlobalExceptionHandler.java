package com.example.trello.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardListException.class)
    public ResponseEntity<ErrorResponse> handleCardListException(CardListException e) {
        ErrorResponse message = ErrorResponse.builder()
                .errorCode(e.getCardListErrorCode().name())
                .message(e.getCardListErrorCode().getMessage())
                .build();
        return new ResponseEntity<>(message, e.getCardListErrorCode().getHttpStatus());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(MethodArgumentNotValidException e) {
        ErrorResponse message = ErrorResponse.builder()
                .errorCode(e.getStatusCode().toString())
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .build();
        return new ResponseEntity<>(message, e.getStatusCode());

    }
}
