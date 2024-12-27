package com.example.trello.cardlist;

import com.example.trello.board.Board;
import com.example.trello.card.Card;
import com.example.trello.cardlist.dto.UpdateCardListRequestDto;
import com.example.trello.cardlist.dto.CardListResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Setter
    @Column(name = "sequence")
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @OneToMany(mappedBy = "cardList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    public static CardListResponseDto toDto(CardList cardList) {
        return CardListResponseDto.builder()
                .id(cardList.getId())
                .sequence(cardList.sequence)
                .build();
    }

    public void update(UpdateCardListRequestDto requestDto, Integer sequence ) {
        this.title = requestDto.getTitle();
        this.sequence = sequence;

    }

    public void downSequence() {
        this.sequence--;
    }

    public void upSequence() {
        this.sequence++;
    }

    public void updateSequence(Integer sequence) {
        this.sequence=sequence;
    }
}
