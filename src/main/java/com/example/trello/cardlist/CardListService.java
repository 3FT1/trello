package com.example.trello.cardlist;

import com.example.trello.board.Board;
import com.example.trello.board.BoardRepository;
import com.example.trello.cardlist.dto.CardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardListService {

    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;

    public CreateCardListResponseDto create(CreateCardListRequestDto requestDto) {
        Board board = boardRepository.findByIdOrElseThrow(requestDto.getBoardId());
        CardList cardList = requestDto.toEntity(requestDto, board);
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
        cardList.update(requestDto);
        return CardList.toDto(cardList);
    }

    @Transactional
    public void delete(Long id) {
        CardList cardList = cardListRepository.findByIdOrElseThrow(id);
        cardListRepository.delete(cardList);
    }
}
