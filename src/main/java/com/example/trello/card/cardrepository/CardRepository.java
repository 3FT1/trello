package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

    default Card findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new RuntimeException());
    }
}
