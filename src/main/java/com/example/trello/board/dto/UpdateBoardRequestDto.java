package com.example.trello.board.dto;

import com.example.trello.board.BoardColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateBoardRequestDto {
    @NotBlank(message = "title 은 Null 일 수 없습니다.")
    @Size(min = 1, max = 50, message = "title 크기는 1에서 50사이여야합니다.")
    private String title;

    private BoardColor color;

    private MultipartFile file;

    public UpdateBoardRequestDto(String title, BoardColor color, MultipartFile file) {
        this.title = title;
        this.color = color;
        this.file = file;
    }
}
