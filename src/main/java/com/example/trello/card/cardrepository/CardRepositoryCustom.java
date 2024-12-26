package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;

import java.util.List;

public interface CardRepositoryCustom {
    List<Card> searchCard(Long cardListId, String title, String description);
}
