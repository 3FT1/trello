package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CardRepositoryCustom {
    List<Card> searchCard(Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId);
}
