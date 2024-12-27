package com.example.trello.board;

import com.example.trello.board.dto.*;
import com.example.trello.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto boardResponseDto = boardService.createBoard(dto, userDetails.getUser().getId());
        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> viewAllBoard(@RequestBody viewAllBoardRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> boardResponseDtoList = boardService.viewAllBoard(dto, userDetails.getUser().getId());
        return new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponseDto> viewBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardDetailResponseDto boardDetailResponseDto = boardService.viewBoard(boardId, userDetails.getUser().getId());
        return new ResponseEntity<>(boardDetailResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody UpdateBoardRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto updatedBoardResponseDto = boardService.updateBoard(boardId, dto, userDetails.getUser().getId());
        return new ResponseEntity<>(updatedBoardResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUser().getId());
        return "보드가 정상적으로 삭제되었습니다.";
    }
}
