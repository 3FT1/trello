package com.example.trello.card;

import com.example.trello.card.requestDto.UpdateCardRequestDto;
import com.example.trello.card.responsedto.CardResponseDto;
import com.example.trello.cardlist.CardList;
import com.example.trello.comment.Comment;
import com.example.trello.workspace_member.WorkspaceMember;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardList cardList;

    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceMember workspaceMember;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;



    @Builder
    public Card(String title, String description, WorkspaceMember workspaceMember, LocalDate startAt, LocalDate endAt,CardList cardList) {
        this.title = title;
        this.description = description;
        this.workspaceMember = workspaceMember;
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
