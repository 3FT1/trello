package com.example.trello.card.requestDto;

import com.example.trello.card.Card;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.WorkspaceMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CardRequestDto {

    private Long cardListId;

    private String title;

    private String description;

    private Long workSpaceMemberId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;

    public CardRequestDto(Long cardListId, String title, String description, Long workspaceMemberId, LocalDate startAt, LocalDate endAt) {
        this.cardListId = cardListId;
        this.title = title;
        this.description = description;
        this.workSpaceMemberId = workspaceMemberId;
        this.startAt = startAt;
        this.endAt = endAt;
    }

}
