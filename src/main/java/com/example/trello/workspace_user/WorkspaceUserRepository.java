package com.example.trello.workspace_user;

import com.example.trello.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser,Long> {

    default WorkspaceUser findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new RuntimeException());
    }

}
