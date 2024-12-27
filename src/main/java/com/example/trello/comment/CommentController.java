package com.example.trello.comment;

import com.example.trello.comment.dto.request.CommentRequestDto;
import com.example.trello.comment.dto.request.UpdateCommentRequestDto;
import com.example.trello.comment.dto.response.CommentResponseDto;
import com.example.trello.config.auth.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(requestDto, userDetails);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentRequestDto requestDto, UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails);
        return new ResponseEntity<>("삭제되었습니다" , HttpStatus.OK);
    }
}
