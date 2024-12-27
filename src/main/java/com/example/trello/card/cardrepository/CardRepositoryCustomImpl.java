package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;
import com.example.trello.card.QCard;
import com.example.trello.card.requestDto.CardListDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardRepositoryCustomImpl implements CardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public CardListDto searchCard(PageRequest pageRequest, Long cardListId, LocalDate startAt, LocalDate endAt, Long boardId) {
        QCard card = QCard.card;


        List<Card> cardList = queryFactory
                .selectFrom(card)
                .where(
                        eqCardListId(cardListId),
                        eqStartAt(startAt),
                        eqEndAt(endAt),
                        eqBoard(boardId))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(card.count())
                .from(card)
                .where(
                        eqCardListId(cardListId),
                        eqStartAt(startAt),
                        eqEndAt(endAt),
                        eqBoard(boardId))
                .fetchOne();
        return new CardListDto(cardList,count);

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
