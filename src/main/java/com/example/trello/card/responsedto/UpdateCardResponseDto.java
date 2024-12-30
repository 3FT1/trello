package com.example.trello.card.responsedto;

import com.example.trello.cardlist.CardList;
import com.example.trello.workspace_member.WorkspaceMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateCardResponseDto {

    private CardList cardList;

    private String title;

    private String description;

    private WorkspaceMember workspaceMember;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;

    public UpdateCardResponseDto(CardList cardListId, String title, String description, WorkspaceMember workspaceMember, LocalDate startAt, LocalDate endAt) {
        this.cardList = cardListId;
        this.title = title;
        this.description = description;
        this.workspaceMember = workspaceMember;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
