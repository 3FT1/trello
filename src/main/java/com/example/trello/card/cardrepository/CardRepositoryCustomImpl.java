package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;
import com.example.trello.card.QCard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardRepositoryCustomImpl implements CardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Card> searchCard(Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId) {
        QCard card = QCard.card;

        return queryFactory
                .selectFrom(card)
                .where(
                        eqCardListId(cardListId),
                        eqStartAt(startAt),
                        eqEndAt(endAt),
                        eqBoard(boardId)
                )
                .fetch();
    }

    private BooleanExpression eqCardListId(Long cardListId) {
        return cardListId != null ? QCard.card.cardList.id.eq(cardListId) : null;
    }

    private BooleanExpression eqStartAt(LocalDate startAt) {
        return startAt != null ? QCard.card.startAt.eq(startAt) : null;
    }

    private BooleanExpression eqEndAt(LocalDate endAt) {
        return endAt != null ? QCard.card.endAt.eq(endAt) : null;
    }

    private BooleanExpression eqBoard(Long boardId) {
        return boardId != null ? QCard.card.cardList.board.id.eq(boardId) : null;
    }
}
