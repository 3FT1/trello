package com.example.trello.cardlist;

import com.example.trello.cardlist.dto.CardListResponseDto;
import com.example.trello.cardlist.dto.CreateCardListRequestDto;
import com.example.trello.cardlist.dto.UpdateCardListRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lists")
public class CardListController {

    private final CardListService cardListService;

    @PostMapping
    public ResponseEntity<CardListResponseDto> createCardList(@RequestBody CreateCardListRequestDto requestDto) {
        CardListResponseDto cardListResponseDto = cardListService.create(requestDto);
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardListResponseDto> findCardList(@PathVariable Long id) {
        CardListResponseDto cardListResponseDto = cardListService.findCardList(id);
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardListResponseDto> updateCardList(@PathVariable Long id, @RequestBody UpdateCardListRequestDto requestDto) {
        CardListResponseDto cardListResponseDto = cardListService.moveSequence(id, requestDto);
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);

    }

    @PatchMapping("/{id}/exchange")
    public ResponseEntity<CardListResponseDto> swapCardList(@PathVariable Long id, @RequestBody UpdateCardListRequestDto requestDto) {
        CardListResponseDto cardListResponseDto = cardListService.swapSequence(id, requestDto);
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardList(@PathVariable Long id) {
        cardListService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
