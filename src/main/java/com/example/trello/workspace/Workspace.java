package com.example.trello.workspace;

import com.example.trello.board.Board;
import com.example.trello.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Workspace() {
    }

    @Builder
    public Workspace(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
