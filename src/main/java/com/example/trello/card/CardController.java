package com.example.trello.card;

import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public ResponseEntity<CardResponseDto> createdCard(CardRequestDto requestDto, HttpServletRequest servletRequest) {
        CardResponseDto responseDto = cardService.createdCardService(requestDto, servletRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

//    @PostMapping("/{cardsId}")
//    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardsId, UpdateCardRequestDto requestDto, HttpServletRequest servletRequest) {
//        CardResponseDto responseDto = cardService.updateCardService(cardsId, requestDto, servletRequest);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }

    @PostMapping("/{cardsId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardsId) {
        cardService.deleteCardService(cardsId);
        return ResponseEntity.ok().body("삭제 완료되었습니다");
    }

//    @PostMapping("/getCards")
//    public ResponseEntity<List<CardResponseDto>> getCards(@PageableDefault(page = 1)Pageable pageable,
//                                                          @RequestParam String title,
//                                                          @RequestParam) {
//
//    }

}
