package com.example.trello.user.enums;

public enum Role {
    USER("user"),
    ADMIN("admin")

    ;
    private final String name;

    Role(String name) {
        this.name = name;
    }
}
