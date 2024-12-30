package com.example.trello.user;

import com.example.trello.common.exception.UserErrorCode;
import com.example.trello.common.exception.UserException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    default User findByIdOrElseThrow(Long userId){
        return findById(userId).orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_ID));
    }

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(()-> new UserException(UserErrorCode.NOT_FOUND_EMAIL));
    }
}

