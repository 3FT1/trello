package com.example.trello.card.requestDto;

import lombok.Getter;

@Getter
public class FileNameRequestDto {

    private String fileName;

    public FileNameRequestDto(String fileName) {
        this.fileName = fileName;
    }
}
