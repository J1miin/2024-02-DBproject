package com.example.bbungeobbang.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
@Entity
@Table(name = "Letter")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer letterId;

    @Column(length = 50, nullable = false)
    private String writerName;

    @Column(length = 255, nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column
    private LocalDateTime unLockTimer;

    @Column
    private Integer createYear;

    @Column
    private Integer createMonth;

    // 외래 키 매핑 (ManyToOne 관계)
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column
    private Boolean isBaked;
    public boolean isBaked() {
        return unLockTimer != null && LocalDateTime.now().isAfter(unLockTimer);
    }
}
