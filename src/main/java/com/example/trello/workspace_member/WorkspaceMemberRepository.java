package com.example.trello.workspace_member;

import com.example.trello.common.exception.WorkspaceMemberErrorCode;
import com.example.trello.common.exception.WorkspaceMemberException;
import com.example.trello.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember,Long> {
    List<WorkspaceMember> findByUserId(Long userId);
    Optional<WorkspaceMember> findByUserIdAndWorkspaceId(Long userId, Long workspaceId);
    Optional<WorkspaceMember> findByIdAndWorkspaceId(Long id, Long workspaceId);
    Boolean existsByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    default WorkspaceMember findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new WorkspaceMemberException(WorkspaceMemberErrorCode.CAN_NOT_FIND_WORKSPACEMEMBER_WITH_WORKSPACEMEMBER_ID));
    }

    default WorkspaceMember findByIdAndWorkspaceIdOrElseThrow(Long id, Long workspaceId){
        return findByIdAndWorkspaceId(id, workspaceId).orElseThrow(() -> new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER));
    }

    default WorkspaceMember findByUserIdAndWorkspaceIdOrElseThrow(Long userId, Long workspaceId) {
        return findByUserIdAndWorkspaceId(userId, workspaceId).orElseThrow(() -> new WorkspaceMemberException(WorkspaceMemberErrorCode.IS_NOT_WORKSPACEMEMBER));
    }
}
