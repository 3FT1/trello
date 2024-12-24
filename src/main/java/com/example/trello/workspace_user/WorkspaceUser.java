package com.example.trello.workspace_user;

import com.example.trello.user.User;
import com.example.trello.workspace.Workspace;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class WorkspaceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private WorkspaceUserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    public WorkspaceUser() {
    }

    @Builder
    public WorkspaceUser(WorkspaceUserRole role, User user, Workspace workspace) {
        this.role = role;
        this.user = user;
        this.workspace = workspace;
    }

    public void updateRole(WorkspaceUserRole role) {
        this.role = role;
    }
}
