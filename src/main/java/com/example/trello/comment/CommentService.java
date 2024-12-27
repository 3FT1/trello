package com.example.trello.comment;

import com.example.trello.card.Card;
import com.example.trello.card.cardrepository.CardRepository;
import com.example.trello.comment.dto.request.CommentRequestDto;
import com.example.trello.comment.dto.request.UpdateCommentRequestDto;
import com.example.trello.comment.dto.response.CommentResponseDto;
import com.example.trello.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;


    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest servletRequest) {

        User user = (User) servletRequest.getSession().getAttribute("id");

        Card card = cardRepository.findByIdOrElseThrow(requestDto.getCardId());

        Comment comment = new Comment(requestDto.getContent(), user.getNickname(), card, user);

        card.getComments().add(comment);

        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getNikeName(), comment.getCard().getId(), comment.getUser().getId());
    }

    public CommentResponseDto updateComment(Long commentId, UpdateCommentRequestDto requestDto, HttpServletRequest servletRequest) {

        User user = (User) servletRequest.getSession().getAttribute("id");

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException();
        }

        comment.updateComment(requestDto.getContent());

        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getNikeName(), comment.getCard().getId(), comment.getUser().getId());

    }

    public void deleteComment(Long commentId, HttpServletRequest servletRequest) {

        User user = (User) servletRequest.getSession().getAttribute("id");

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException();
        }

        commentRepository.delete(comment);
    }
}
