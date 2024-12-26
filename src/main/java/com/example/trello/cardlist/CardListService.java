package com.example.trello.cardlist;

import com.example.trello.board.Board;
import com.example.trello.board.BoardRepository;
import com.example.trello.cardlist.dto.UpdateCardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListRequestDto;
import com.example.trello.cardlist.dto.CardListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardListService {

    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;

    private Integer maxSequence; // 보드에서 가장 큰 순서

    @Transactional
    public CardListResponseDto create(CreateCardListRequestDto requestDto) {
        Board board = boardRepository.findByIdOrElseThrow(requestDto.getBoardId());
        maxSequence = cardListRepository.findByMax(board.getId()).orElse(0);
        CardList cardList = requestDto.toEntity(requestDto, board, ++maxSequence);
        CardList savedCardList = cardListRepository.save(cardList);

        return CardList.toDto(savedCardList);
    }


    public CardListResponseDto findCardList(Long id) { //todo dto 이름 바꾸기
        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        return CardList.toDto(cardList);
    }

    @Transactional
    public CardListResponseDto moveSequence(Long id, UpdateCardListRequestDto requestDto) {
        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        Integer currentSequence=cardList.getSequence();

        List<CardList> cardLists = cardListRepository.findAllByBoardId(cardList.getBoard().getId());

        if(currentSequence!=requestDto.getSequence()) {
            if(currentSequence<requestDto.getSequence()) {
                cardLists.stream()
                        .filter(e->e.getSequence()>currentSequence)
                        .filter(e->e.getSequence()<=requestDto.getSequence())
                        .forEach(e->e.downSequence());
            }else {
                cardLists.stream()
                        .filter(e->e.getSequence()<currentSequence)
                        .filter(e->e.getSequence()>=requestDto.getSequence())
                        .forEach(e->e.upSequence());
            }
        }

        if (requestDto.getSequence()>cardLists.size()){

            throw  new RuntimeException();
        }

        return CardList.toDto(cardList);
    }

    @Transactional
    public CardListResponseDto swapSequence(Long id, UpdateCardListRequestDto requestDto) {
        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        CardList exchangeCardList = cardListRepository.findBySequenceAndBoardIdOrElseThrow(requestDto.getSequence(),cardList.getBoard().getId());
        Integer temp = cardList.getSequence();
        cardList.update(requestDto, exchangeCardList.getSequence());
        exchangeCardList.updateSequence(temp);

        return CardList.toDto(cardList);
    }


    @Transactional
    public void delete(Long id) {

        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        Board board = boardRepository.findByIdOrElseThrow(cardList.getBoard().getId());
        Integer deleteSequence = cardList.getSequence();
        cardListRepository.delete(cardList);
        List<CardList> cardLists = cardListRepository.findAllByBoardId(board.getId());
        for (CardList list : cardLists) {
            if (list.getSequence() > deleteSequence) {
                list.downSequence();
            }
        }
    }


}
