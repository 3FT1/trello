package com.example.trello.workspace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSpaceRepository extends JpaRepository<Workspace, Long> {

    default Workspace findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()->new RuntimeException());
    }

}
