package com.example.bbungeobbang.Controller;

import com.example.bbungeobbang.DTO.FishDTO;
import com.example.bbungeobbang.Entity.Letter;
import com.example.bbungeobbang.Repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FishController {
    @Autowired
    private LetterRepository letterRepository;

    @GetMapping("/fishStates/{userId}")
    public ResponseEntity<?> getFishStates(@PathVariable String userId) {
        System.out.println("userId:"+userId);
        // 로그인된 사용자가 요청한 경우
        List<Letter> letters = letterRepository.findAllByUserId(userId);

        List<FishDTO> fishDTOs = letters.stream()
                .map(letter -> new FishDTO(
                        letter.getLetterId(),
                        letter.getWriterName(),
                        letter.getCreateDate(),
                        letter.getUnLockTimer(),
                        letter.getIsBaked(),
                        letter.getContents()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(fishDTOs);
    }
}
