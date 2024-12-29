package com.example.trello.cardlist;

import com.example.trello.board.Board;
import com.example.trello.common.exception.CardListException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.trello.common.exception.CardListErrorCode.CARD_LIST_NOT_FOUND;

@Repository
public interface CardListRepository extends JpaRepository<CardList, Long> {
    List<CardList> findByBoard(Board board);

    default CardList findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new CardListException(CARD_LIST_NOT_FOUND));
    }

    @Query(value = "select max(cardlist.sequence)from CardList cardlist where cardlist.board.id = :board_id")
    Optional<Integer> findByMax(@Param("board_id") Long board_id);

    Optional<CardList> findBySequenceAndBoardId(Integer sequence, Long boardId);

    default CardList findBySequenceAndBoardIdOrElseThrow(Integer sequence, Long boardId) {
        return findBySequenceAndBoardId(sequence, boardId).orElseThrow(() -> new CardListException(CARD_LIST_NOT_FOUND));
    }

    List<CardList> findAllByBoardId(Long boardId);

}

