package com.example.trello.user;

import com.example.trello.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    default User findByIdOrElseThrow(Long userId){
        return findById(userId).orElseThrow(()-> new RuntimeException());
    }

    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(()-> new RuntimeException());
    }
}

