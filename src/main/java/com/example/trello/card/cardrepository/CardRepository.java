package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;
import com.example.trello.cardlist.CardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

    List<Card> findByCardList(CardList cardList);

    default Card findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new RuntimeException());
    }
}