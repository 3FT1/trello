package com.example.trello.card;

import com.example.trello.cardlist.CardList;
import com.example.trello.comment.Comment;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "order")
    private Integer order;

    @Column(name = "image")
    private String image;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardList cardList;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;


}
