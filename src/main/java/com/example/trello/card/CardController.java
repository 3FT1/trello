package com.example.trello.card;

import com.example.trello.card.requestDto.CardListDto;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDto> createdCard(@RequestBody CardRequestDto requestDto, HttpServletRequest servletRequest) {
        CardResponseDto responseDto = cardService.createdCardService(requestDto, servletRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{cardsId}/updateCards")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardsId, @RequestBody UpdateCardRequestDto requestDto) {
        CardResponseDto responseDto = cardService.updateCardService(cardsId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cardsId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardsId) {
        cardService.deleteCardService(cardsId);
        return new ResponseEntity<>("삭제 완료되었습니다", HttpStatus.OK);
    }

    @GetMapping("/{cardsId}")
    public ResponseEntity<CardResponseDto> findCard(@PathVariable Long cardsId) {
        CardResponseDto responseDto = cardService.findCardById(cardsId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getCards")
    public ResponseEntity<CardListDto> getCards(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(required = false) Long cardListId,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startAt,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endAt,
                                          @RequestParam(required = false) Long boardId) {
        CardListDto cards = cardService.searchCards(page, cardListId, startAt, endAt, boardId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
