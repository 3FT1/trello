package com.example.trello.card.cardrepository;

import com.example.trello.card.requestDto.CardPageDto;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

public interface CardRepositoryCustom {
    CardPageDto searchCard(PageRequest pageRequest, Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId);
}
