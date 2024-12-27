package com.example.trello.comment;

import com.example.trello.card.Card;
import com.example.trello.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "nikeName")
    private String nikeName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Comment(String content, String nikeName, Card card, User user) {
        this.content = content;
        this.nikeName = nikeName;
        this.card = card;
        this.user = user;
    }

}
