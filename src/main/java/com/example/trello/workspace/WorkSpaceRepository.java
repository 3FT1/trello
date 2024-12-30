package com.example.trello.workspace;

import com.example.trello.common.exception.WorkspaceErrorCode;
import com.example.trello.common.exception.WorkspaceException;
import com.example.trello.common.exception.WorkspaceMemberErrorCode;
import com.example.trello.common.exception.WorkspaceMemberException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSpaceRepository extends JpaRepository<Workspace, Long> {

    default Workspace findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new WorkspaceException(WorkspaceErrorCode.CAN_NOT_FIND_WORKSPACE_WITH_WORKSPACE_ID));
    }

}
