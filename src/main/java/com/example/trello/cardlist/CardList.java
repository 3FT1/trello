package com.example.trello.cardlist;

import com.example.trello.board.Board;
import com.example.trello.card.Card;
import com.example.trello.cardlist.dto.CardListRequestDto;
import com.example.trello.cardlist.dto.CreateCardListResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "sequence")
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @OneToMany(mappedBy = "cardList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    public static CreateCardListResponseDto toDto(CardList cardList) {
        return CreateCardListResponseDto.builder()
                .id(cardList.getId())
                .sequence(cardList.sequence)
                .build();
    }

    public void update(CardListRequestDto requestDto,Integer sequence ) {
        this.title = requestDto.getTitle();
        this.sequence = sequence;

    }

    public void sortSequence(Integer sequence) {
        this.sequence=sequence-1;
    }

    public void updateSequence(Integer sequence) {
        this.sequence=sequence;
    }
}
