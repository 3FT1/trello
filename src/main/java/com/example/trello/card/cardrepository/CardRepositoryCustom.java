package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;
import com.example.trello.card.requestDto.CardListDto;
import com.example.trello.card.responsedto.CardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CardRepositoryCustom {
    CardListDto searchCard(PageRequest pageRequest, Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId);
}
