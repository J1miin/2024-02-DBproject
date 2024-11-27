package com.example.bbungeobbang.Repository;

import com.example.bbungeobbang.Entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    @Query("SELECT COUNT(el) FROM Letter el WHERE el.user.userId = :userId")
    Long countLettersByUserId(@Param("userId") String userId);
    @Query("SELECT el FROM Letter el WHERE el.user.userId = :userId")
    List<Letter> findAllByUserId(@Param("userId") String userId);
}
