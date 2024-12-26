package com.example.trello.card;

import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.comment.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

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

    @Column(name = "start_at")
    @Pattern(regexp = "yyyy-mm-dd")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    @Pattern(regexp = "yyyy-mm-dd")
    private LocalDateTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardList cardList;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

    public Card(String title, String description, CardList cardList) {
        this.title = title;
        this.description = description;
        this.cardList = cardList;
    }

//    public void updateCard(UpdateCardRequestDto requestDto) {
//        this.cardList.getId() = requestDto.getCardListId();
//    }
}
