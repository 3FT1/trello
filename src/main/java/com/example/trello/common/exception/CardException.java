package com.example.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardException extends RuntimeException {
    private CardErrorCode cardErrorCode;
}
