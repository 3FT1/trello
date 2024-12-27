package com.example.trello.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByWorkspaceId(Long workspaceId);

    default Board findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new RuntimeException());
    }
}
