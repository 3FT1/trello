package com.example.trello.cardlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardListRepository extends JpaRepository<CardList, Long> {

    default CardList findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException());
    }
}

