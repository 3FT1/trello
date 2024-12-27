package com.example.trello.cardlist;

import com.example.trello.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardListRepository extends JpaRepository<CardList, Long> {
    List<CardList> findByBoard(Board board);

    default CardList findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException());
    }
    @Query(value = "select MAX(sequence)from card_list ",nativeQuery = true)
    Optional<Integer> findByMax();

    Optional<CardList> findBySequence(Integer sequence);

    default CardList findBySequenceOrElseThrow(Integer sequence){
        return findBySequence(sequence).orElseThrow(()->new RuntimeException());
    }
}

