package com.example.bbungeobbang.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class FishDTO {
    private Integer letterId;
    private String writerName;
    private LocalDateTime createTime;
    private LocalDateTime unLockTimer;

    private Boolean isBaked;
    private String contents;

    public FishDTO(Integer letterId, String writerName, LocalDateTime createTime,LocalDateTime unLockTimer, Boolean isBaked, String contents) {
        this.letterId = letterId;
        this.writerName = writerName;
        this.unLockTimer = unLockTimer;
        this.isBaked = isBaked;
        this.contents = contents;
        this.createTime =createTime;
    }
}
