package com.example.trello.card;

import com.example.trello.card.responsedto.CardPageResponseDto;
import com.example.trello.card.requestDto.CardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;


@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;


    /**
     * 카드 CRUD
     */

    @PostMapping
    public ResponseEntity<CardResponseDto> createdCard(@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto responseDto = cardService.createdCardService(requestDto, userDetails);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId, @RequestBody UpdateCardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto responseDto = cardService.updateCardService(cardId, requestDto, userDetails);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCardService(cardId, userDetails);
        return new ResponseEntity<>("삭제 완료되었습니다", HttpStatus.OK);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> findCard(@PathVariable Long cardId) {
        CardResponseDto responseDto = cardService.findCardById(cardId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CardPageResponseDto> getCards(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(required = false) Long cardListId,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startAt,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endAt,
                                                        @RequestParam(required = false) Long boardId) {
        CardPageResponseDto cards = cardService.searchCards(page, cardListId, startAt, endAt, boardId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }


    /**
     * 파일 업로드
     */

    @PostMapping(value = "/{cardId}/addAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAttachments(@PathVariable Long cardId,
                                                     @RequestPart("file") MultipartFile file,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String fileUrl = cardService .uploadFile(cardId, file, userDetails);
        return new ResponseEntity<>(fileUrl + "업로드 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}/deleteAttachment")
    public ResponseEntity<String> deleteFile(@PathVariable Long cardId,
                                              @RequestBody String fileName,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteFile(cardId, fileName, userDetails);
        return new ResponseEntity<>("삭제 완료되었습니다", HttpStatus.OK);
    }

//    @GetMapping("/{cardId}/getAttachment")
//    public ResponseEntity<String> getFileUrl(@PathVariable Long cardId,
//                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return new ResponseEntity<>(cardService.getFile(cardId, userDetails), HttpStatus.OK);
//
//    }
}
