package com.example.bbungeobbang.Controller;

import com.example.bbungeobbang.Entity.Letter;
import com.example.bbungeobbang.Entity.User;
import com.example.bbungeobbang.Repository.LetterRepository;
import com.example.bbungeobbang.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class YearController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LetterRepository letterRepository;

    //연도별 붕어빵 모아보기 페이지
    @GetMapping("/year/{userId}")
    public String showYear(@PathVariable String userId, Model model) {
        // 현재 로그인된 사용자 정보 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 확인 및 인증된 사용자 ID 가져오기
        if (!authentication.isAuthenticated()) {
            return "redirect:/login"; // 로그인되지 않았으면 로그인 페이지로 리다이렉트
        }

        String loggedInUserId = authentication.getName(); // Security 설정에 따라 username을 userId로 간주

        // 경로의 userId와 로그인한 사용자가 다를 경우 에러 처리 (권한 체크)
        if (!loggedInUserId.equals(userId)) {
            return "access-denied";
        }

        // 사용자 정보 가져오기
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String nickname = user.getNickname();
        model.addAttribute("nickname", nickname);

        return "year";
    }

    @GetMapping("/year/{userId}/{year}")
    public String showYearByYear(@PathVariable String userId, @PathVariable int year, Model model) {
        Optional<User> user = userRepository.findByUserId(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String loggedInUserId = authentication.getName();
        if (!loggedInUserId.equals(userId)) {
            return "access-denied";
        }

        // 연도별 편지 데이터 조회
        List<Letter> letterByYear = letterRepository.findLettersByYearAndMonth(userId, year, 11,1); // 2023년 11월 ~ 2024년 1월
         // 모델에 데이터 추가
        model.addAttribute("letters", letterByYear);

        // 사용자 정보
        String nickname = user.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")).getNickname();
        model.addAttribute("nickname", nickname);

        return "yearLetter";
    }

}
