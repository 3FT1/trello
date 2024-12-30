package com.example.trello.comment;

import com.example.trello.board.Board;
import com.example.trello.common.exception.CommentErrorCode;
import com.example.trello.common.exception.CommentException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }
}
