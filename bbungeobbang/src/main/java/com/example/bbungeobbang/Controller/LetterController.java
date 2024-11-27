package com.example.bbungeobbang.Controller;

import com.example.bbungeobbang.Entity.Letter;
import com.example.bbungeobbang.Entity.User;
import com.example.bbungeobbang.Repository.LetterRepository;
import com.example.bbungeobbang.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LetterController {

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private UserRepository userRepository;

    // GET 편지 작성 페이지 이동
    @GetMapping("/{userId}/letter/write")
    public String showWritePage(Model model, @PathVariable String userId) {
        model.addAttribute("writerName"); // 기본값 설정
        model.addAttribute("userId", userId);
        return "write"; // write.html 반환
    }

    // POST 편지 작성
    @Transactional
    @PostMapping("/{userId}/letter/write")
    public String saveLetter(
            @PathVariable("userId") String recipientUserId,
            @RequestParam("writerName") String writerName,
            @RequestParam("contents") String contents
    ) {
        // 수신자 User ID 확인
        User recipient = userRepository.findByUserId(recipientUserId)
                .orElseThrow(() -> new RuntimeException("수신자 없음"));

        // 편지 저장 로직
        Letter letter = new Letter();
        letter.setWriterName(writerName.isEmpty() ? "익명" : writerName);
        letter.setContents(contents);
        letter.setCreateDate(LocalDateTime.now());
        letter.setCreateYear(LocalDateTime.now().getYear());
        letter.setCreateMonth(LocalDateTime.now().getMonthValue());
        letter.setUnLockTimer(LocalDateTime.now().plusHours(1)); // 1시간 뒤 잠금 해제
        letter.setIsBaked(false);
        letter.setUser(recipient);

        letterRepository.save(letter);

        return "redirect:/main/{userId}";
    }

    // GET 편지 읽기 - 로그인 사용자만 가능
    @GetMapping("/{userId}/letter/read/{letterId}")
    public String showReadPage(Model model, @PathVariable String userId, @PathVariable Long letterId) {
        model.addAttribute("userId", userId);
        return "read";
    }

}