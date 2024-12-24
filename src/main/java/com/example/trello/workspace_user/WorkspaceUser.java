package com.example.trello.workspace_user;

import com.example.trello.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WorkspaceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private WorkspaceUser workspaceUser;


}
