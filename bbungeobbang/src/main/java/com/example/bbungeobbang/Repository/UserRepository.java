package com.example.bbungeobbang.Repository;

import com.example.bbungeobbang.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByNickname(String nickname);
    Optional<User> findByUserId(String userId);
}