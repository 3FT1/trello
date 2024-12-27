package com.example.trello.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardListException extends RuntimeException {
    private CardListErrorCode cardListErrorCode;
}
