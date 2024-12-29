package com.example.trello.user;

import com.example.trello.user.enums.AccountStatus;
import com.example.trello.user.enums.Role;
import com.example.trello.workspace.Workspace;
import com.example.trello.workspace_member.WorkspaceMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToMany(mappedBy = "user")
    private List<WorkspaceMember> workspaceMembers;

    @Builder
    public User(String email, String password, String nickname, Role role, AccountStatus status) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.status = status;
    }

    public void deletedAccount(AccountStatus status) {
        this.status = status;
    }

    public boolean isDeletedAccount(User user) {
        return user.getStatus().equals(AccountStatus.DELETED);
    }
}
