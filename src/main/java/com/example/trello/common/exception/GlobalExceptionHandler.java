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

    @ExceptionHandler(CardListException.class)
    public ResponseEntity<ErrorResponse> handleCardListException(WorkspaceException e) {
        ErrorResponse message = ErrorResponse.builder()
                .errorCode(e.getWorkspaceErrorCode().name())
                .message(e.getWorkspaceErrorCode().getMessage())
                .build();
        return new ResponseEntity<>(message, e.getWorkspaceErrorCode().getHttpStatus());

    }

    @ExceptionHandler(CardListException.class)
    public ResponseEntity<ErrorResponse> handleCardListException(WorkspaceMemberException e) {
        ErrorResponse message = ErrorResponse.builder()
                .errorCode(e.getWorkspaceMemberErrorCode().name())
                .message(e.getWorkspaceMemberErrorCode().getMessage())
                .build();
        return new ResponseEntity<>(message, e.getWorkspaceMemberErrorCode().getHttpStatus());

    }

    @ExceptionHandler(CardListException.class)
    public ResponseEntity<ErrorResponse> handleCardListException(BoardException e) {
        ErrorResponse message = ErrorResponse.builder()
                .errorCode(e.getBoardErrorCode().name())
                .message(e.getBoardErrorCode().getMessage())
                .build();
        return new ResponseEntity<>(message, e.getBoardErrorCode().getHttpStatus());

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
