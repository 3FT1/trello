package com.example.trello.comment;

import com.example.trello.card.Card;
import com.example.trello.user.User;
import com.example.trello.workspace_member.WorkspaceMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceMember workspaceMember;

    public Comment(String content, Card card, User user) {
        this.content = content;
        this.card = card;
        this.user = user;
    }

    public void updateComment(String content) {
        this.content = content;
    }

}
