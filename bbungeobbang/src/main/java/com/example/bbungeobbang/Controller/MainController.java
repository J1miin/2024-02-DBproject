package com.example.bbungeobbang.Controller;

import com.example.bbungeobbang.Entity.Letter;
import com.example.bbungeobbang.Entity.User;
import com.example.bbungeobbang.Repository.LetterRepository;
import com.example.bbungeobbang.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LetterRepository letterRepository;

    //붕어가 있는 Main 화면
    @GetMapping("/main/{userId}")
    public String mainUser(@PathVariable String userId, Model model) {
        Optional<User> user = userRepository.findByUserId(userId);
        List<Letter> letters = letterRepository.findAllByUserId(userId);

        // 로그인 여부 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = false;

// 인증된 사용자가 있을 때만 isLoggedIn을 true로 설정
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            isLoggedIn = true;
        }

// 로그인 상태를 모델에 추가
        model.addAttribute("isLoggedIn", isLoggedIn);

        System.out.println("로그인 한 사용자 여부: " + isLoggedIn);

// 유저 정보가 있을 때만 추가 처리
        if (user.isPresent()) {
            model.addAttribute("nickname", user.get().getNickname());
            model.addAttribute("breadCount", letterRepository.countLettersByUserId(userId));
            model.addAttribute("letters", letters);
            model.addAttribute("userId", userId);
        }

        return "main";
    }
}