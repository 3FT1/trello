package com.example.trello.board;

import com.example.trello.board.dto.*;
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

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponseDto> viewBoard(@PathVariable Long boardId, @SessionAttribute("id") Long loginUserId) {
        BoardDetailResponseDto boardDetailResponseDto = boardService.viewBoard(boardId, loginUserId);
        return new ResponseEntity<>(boardDetailResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody UpdateBoardRequestDto dto, @SessionAttribute("id") Long loginUserId) {
        BoardResponseDto updatedBoardResponseDto = boardService.updateBoard(boardId, dto.getTitle(), dto.getColor(), dto.getImage(), loginUserId);
        return new ResponseEntity<>(updatedBoardResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, @SessionAttribute("id") Long loginUserId) {
        boardService.deleteBoard(boardId, loginUserId);
        return "보드가 정상적으로 삭제되었습니다.";
    }
}
