package com.example.trello.card;

import com.example.trello.card.requestDto.CardPageDto;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDto> createdCard(@RequestBody CardRequestDto requestDto, @SessionAttribute("id") Long userid) {
        CardResponseDto responseDto = cardService.createdCardService(requestDto, userid);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{cardsId}/updateCards")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardsId, @RequestBody UpdateCardRequestDto requestDto, @SessionAttribute("id") Long userid) {
        CardResponseDto responseDto = cardService.updateCardService(cardsId, requestDto, userid);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cardsId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardsId, @SessionAttribute("id") Long userid) {
        cardService.deleteCardService(cardsId, userid);
        return new ResponseEntity<>("삭제 완료되었습니다", HttpStatus.OK);
    }

    @GetMapping("/{cardsId}")
    public ResponseEntity<CardResponseDto> findCard(@PathVariable Long cardsId) {
        CardResponseDto responseDto = cardService.findCardById(cardsId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CardPageDto> getCards(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(required = false) Long cardListId,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startAt,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endAt,
                                                @RequestParam(required = false) Long boardId) {
        CardPageDto cards = cardService.searchCards(page, cardListId, startAt, endAt, boardId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
