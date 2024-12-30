package com.example.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentException extends RuntimeException {
    private CommentErrorCoed commentErrorCoed;
}
