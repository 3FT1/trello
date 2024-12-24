package com.example.trello.card;

import com.example.trello.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    default Card findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new RuntimeException());
    }
}
