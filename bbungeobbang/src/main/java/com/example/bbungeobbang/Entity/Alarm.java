package com.example.bbungeobbang.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
@Entity
@Table(name = "Alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer notificationId;

    @Column(length = 255, nullable = false)
    private String alarmContents;

    @Column(nullable = false)
    private LocalDate alarmDate;

    @Column(name = "letterId", nullable = false)
    private Integer letterId;

    @Column(name = "isBaked", columnDefinition = "TINYINT")
    private Boolean isBaked;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

}
