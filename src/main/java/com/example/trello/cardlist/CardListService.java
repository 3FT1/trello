package com.example.trello.cardlist;

import com.example.trello.board.Board;
import com.example.trello.board.BoardRepository;
import com.example.trello.cardlist.dto.CardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardListService {

    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;

    private static Integer MAX_SEQUENCE;

    @Transactional
    public CreateCardListResponseDto create(CreateCardListRequestDto requestDto) {
        Board board = boardRepository.findByIdOrElseThrow(requestDto.getBoardId());
        MAX_SEQUENCE = cardListRepository.findByMax().orElse(0);
        CardList cardList = requestDto.toEntity(requestDto, board, ++MAX_SEQUENCE);
        CardList savedCardList = cardListRepository.save(cardList);

        return CardList.toDto(savedCardList);
    }


    public CreateCardListResponseDto findCardList(Long id) {
        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        return CardList.toDto(cardList);
    }

    @Transactional
    public CreateCardListResponseDto update(Long id, CardListRequestDto requestDto) {
        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        CardList exchangeCardList = cardListRepository.findBySequenceOrElseThrow(requestDto.getSequence());
        Integer temp = cardList.getSequence();
        cardList.update(requestDto, exchangeCardList.getSequence());
        exchangeCardList.updateSequence(temp);

        return CardList.toDto(cardList);
    }

    @Transactional
    public void delete(Long id) {
        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        Integer deleteSequence = cardList.getSequence();
        cardListRepository.delete(cardList);
        List<CardList> cardLists = cardListRepository.findAll();
        for (CardList list : cardLists) {
            if (list.getSequence() > deleteSequence) {
                list.sortSequence(list.getSequence());
            }
        }
    }


}
