package com.example.trello.board.dto;

import com.example.trello.board.BoardColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BoardRequestDto {
    @NotBlank(message = "title 은 Null 일 수 없습니다.")
    @Size(min = 1, max = 50, message = "title 크기는 1에서 50사이여야합니다.")
    private String title;

    private BoardColor color;
    @NotNull
    private Long workspaceId;
    private MultipartFile file;

    public BoardRequestDto(String title, BoardColor color, Long workspaceId, MultipartFile file) {
        this.title = title;
        this.color = color;
        this.workspaceId = workspaceId;
        this.file = file;
    }
}
