package com.example.trello.workspace_member;

import com.example.trello.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember,Long> {
    List<WorkspaceMember> findByUser(User user);
    Optional<WorkspaceMember> findByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    default WorkspaceMember findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new RuntimeException());
    }

    default WorkspaceMember findByUserIdAndWorkspaceIdOrElseThrow(Long userId, Long workspaceId) {
        return findByUserIdAndWorkspaceId(userId, workspaceId).orElseThrow(() -> new RuntimeException());
    }
}
