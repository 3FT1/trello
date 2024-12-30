package com.example.trello.board;

import com.example.trello.common.exception.BoardErrorCode;
import com.example.trello.common.exception.BoardException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByWorkspaceId(Long workspaceId);

    default Board findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new BoardException(BoardErrorCode.CAN_NOT_FIND_BOARD_WITH_BOARD_ID));
    }
}
