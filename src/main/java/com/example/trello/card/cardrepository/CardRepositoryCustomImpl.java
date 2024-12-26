package com.example.trello.card.cardrepository;

import com.example.trello.card.Card;
import com.example.trello.card.QCard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardRepositoryCustomImpl implements CardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Card> searchCard(Long cardListId, String title, String description) {
        QCard card = QCard.card;

        return queryFactory
                .selectFrom(card)
                .where(
                        eqCardListId(cardListId),
                        eqTitle(title),
                        eqDescription(description)
                )
                .fetch();
    }

    private BooleanExpression eqCardListId(Long cardListId) {
        return cardListId != null ? QCard.card.cardList.id.eq(cardListId) : null;
    }

    private BooleanExpression eqTitle(String title) {
        return title != null ? QCard.card.title.eq(title) : null;
    }

    private BooleanExpression eqDescription(String description) {
        return description != null ? QCard.card.description.eq(description) : null;
    }
}
