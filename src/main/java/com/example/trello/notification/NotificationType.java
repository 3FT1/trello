package com.example.trello.notification;

import lombok.Getter;

@Getter
public enum NotificationType {

    ADD_MEMBER("맴버가 추가되었습니다"),UPDATE_CARD("카드가 변경되었습니다"),CREATE_COMMENT("새로운 댓글이 생성되었습니다");

    private String message;

    NotificationType(String message) {
        this.message = message;
    }

    public static String createMessage(NotificationType notificationType) {
        return "{\"text\" : " + " \""+notificationType.getMessage()+"\" }";
    }
}
