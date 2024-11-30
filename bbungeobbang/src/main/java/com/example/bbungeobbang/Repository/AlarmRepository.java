package com.example.bbungeobbang.Repository;

import com.example.bbungeobbang.Entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    @Query("SELECT al.alarmContents FROM Alarm al WHERE al.user.userId = :userId")
    List<String> findAlarmContentsByUserId(@Param("userId") String userId);
    @Query("SELECT al.isBaked from Alarm al where al.user.userId=:userId")
    List<Boolean> findIsBakedByUserId(@Param("userId") String userId);
    @Query("SELECT al.letterId from Alarm al where al.user.userId=:userId")
    List<Integer> findLetterIdByUserId(@Param("userId") String userId);
}

