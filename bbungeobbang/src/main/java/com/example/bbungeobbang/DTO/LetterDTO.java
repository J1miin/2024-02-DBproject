package com.example.bbungeobbang.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LetterDTO {
    private Integer letterId;
    private String writerName;
    private String contents;
    private LocalDateTime createDate;
    private Boolean isBaked;
}
