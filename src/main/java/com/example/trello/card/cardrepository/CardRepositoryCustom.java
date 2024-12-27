package com.example.trello.card.cardrepository;

import com.example.trello.card.responsedto.CardPageResponseDto;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

public interface CardRepositoryCustom {
    CardPageResponseDto searchCard(PageRequest pageRequest, Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId);
}
