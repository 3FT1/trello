package com.example.trello.card;

import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.comment.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@RequiredArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "userName")
    private String nikeName;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardList cardList;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

    public Card(String title, String description, String nikeName, LocalDate startAt, LocalDate endAt,CardList cardList) {
        this.title = title;
        this.description = description;
        this.nikeName = nikeName;
        this.startAt = startAt;
        this.endAt = endAt;
        this.cardList = cardList;
    }

    public void updateCard(CardList cardList, String title, String description, LocalDate startAt, LocalDate endAt) {
        this.cardList = cardList;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
