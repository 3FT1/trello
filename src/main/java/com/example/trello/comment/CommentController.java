package com.example.trello.comment;

import com.example.trello.comment.dto.request.CommentRequestDto;
import com.example.trello.comment.dto.response.CommentResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest servletRequest) {
        CommentResponseDto responseDto = commentService.createComment(requestDto, servletRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
