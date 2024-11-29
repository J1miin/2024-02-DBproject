package com.example.bbungeobbang.Controller;

import com.example.bbungeobbang.Entity.Letter;
import com.example.bbungeobbang.Entity.User;
import com.example.bbungeobbang.Repository.LetterRepository;
import com.example.bbungeobbang.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    //읽기
    @GetMapping("/main/{userId}/read/{letterId}")
    public String showReadPage(Model model, @PathVariable String userId, @PathVariable Integer letterId) {

        // SecurityContext에서 Authentication 객체 가져오기
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // 인증된 사용자인지 확인
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 로그인된 사용자 ID 가져오기
        String loggedInUserId;
        if (authentication.getPrincipal() instanceof UserDetails) {
            loggedInUserId = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            loggedInUserId = authentication.getPrincipal().toString();
        }

        // 로그인된 사용자와 요청된 사용자 비교
        if (!loggedInUserId.equals(userId)) {
            model.addAttribute("error", "권한이 없습니다.");
            return "error"; // 에러 페이지로 이동
        }

        // 편지 조회
        Optional<Letter> optionalLetter = letterRepository.findById(letterId);
        if (optionalLetter.isPresent()) {
            Letter letter = optionalLetter.get();

            model.addAttribute("writerName", letter.getWriterName());
            model.addAttribute("contents", letter.getContents());
            model.addAttribute("letter", letter);
            model.addAttribute("userId", userId);
        } else {
            model.addAttribute("error", "편지를 찾을 수 없습니다.");
            return "error"; // 편지가 없는 경우 에러 페이지로 이동
        }

        return "read"; // 읽기 페이지로 이동
    }


}
