package com.example.trello.cardlist;

import com.example.trello.board.Board;
import com.example.trello.card.Card;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class CardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "order")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @OneToMany(mappedBy = "cardList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;
}
