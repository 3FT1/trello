package com.example.trello.workspace_member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember,Long> {

    default WorkspaceMember findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new RuntimeException());
    }

}
