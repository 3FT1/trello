package com.example.trello.workspace_member;

import com.example.trello.user.User;
import com.example.trello.workspace.Workspace;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkspaceMemberRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    public WorkspaceMember() {
    }

    @Builder
    public WorkspaceMember(WorkspaceMemberRole role, User user, Workspace workspace) {
        this.role = role;
        this.user = user;
        this.workspace = workspace;
    }

    public void updateRole(WorkspaceMemberRole role) {
        this.role = role;
    }
}
