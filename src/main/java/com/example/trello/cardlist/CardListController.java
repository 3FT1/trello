package com.example.trello.cardlist;

import com.example.trello.cardlist.dto.CardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListResponseDto;
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
    public ResponseEntity<CreateCardListResponseDto> createCardList(@RequestBody CreateCardListRequestDto requestDto) {
        CreateCardListResponseDto createCardListResponseDto = cardListService.create(requestDto);
        return new ResponseEntity<>(createCardListResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateCardListResponseDto> findCardList(@PathVariable Long id) {
        CreateCardListResponseDto cardListResponseDto = cardListService.findCardList(id);
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreateCardListResponseDto> updateCardList(@PathVariable Long id, @RequestBody CardListRequestDto requestDto) {
        CreateCardListResponseDto createCardListResponseDto = cardListService.update(id, requestDto);
        return new ResponseEntity<>(createCardListResponseDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardList(@PathVariable Long id) {
        cardListService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
