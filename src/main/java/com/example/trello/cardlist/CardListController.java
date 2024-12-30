package com.example.trello.cardlist;

import com.example.trello.cardlist.dto.CardListResponseDto;
import com.example.trello.cardlist.dto.CreateCardListRequestDto;
import com.example.trello.cardlist.dto.UpdateCardListRequestDto;
import com.example.trello.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lists")
public class CardListController {

    private final CardListService cardListService;

    @PostMapping
    public ResponseEntity<CardListResponseDto> createCardList(@RequestBody CreateCardListRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardListResponseDto cardListResponseDto = cardListService.create(requestDto,userDetails.getUser());
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardListResponseDto> findCardList(@PathVariable Long id) {
        CardListResponseDto cardListResponseDto = cardListService.findCardList(id);
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CardListResponseDto> updateCardList(@PathVariable Long id, @RequestBody UpdateCardListRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardListResponseDto cardListResponseDto = cardListService.moveSequence(id, requestDto,userDetails.getUser());
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);

    }

    @PatchMapping("/{id}/exchange")
    public ResponseEntity<CardListResponseDto> swapCardList(@PathVariable Long id, @RequestBody UpdateCardListRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardListResponseDto cardListResponseDto = cardListService.swapSequence(id, requestDto,userDetails.getUser());
        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardList(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardListService.delete(id,userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
