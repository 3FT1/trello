package com.example.trello.board;

import com.example.trello.cardlist.CardList;
import com.example.trello.workspace.Workspace;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardList> cardLists;

}
