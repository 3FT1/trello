package com.example.trello.board;

import com.example.trello.board.dto.BoardRequestDto;
import com.example.trello.board.dto.BoardResponseDto;
import com.example.trello.board.dto.viewAllBoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto dto, @SessionAttribute("id") Long loginUserId) {
        BoardResponseDto boardResponseDto = boardService.createBoard(dto.getWorkspaceId(), dto.getTitle(), dto.getColor(), dto.getImage(), loginUserId);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> viewAllBoard(@RequestBody viewAllBoardRequestDto dto, @SessionAttribute("id") Long loginUserId) {
        List<BoardResponseDto> boardResponseDtoList = boardService.viewAllBoard(dto.getWorkspaceId(), loginUserId);
        return new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }
}
