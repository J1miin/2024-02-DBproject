package com.example.bbungeobbang.Controller;

import com.example.bbungeobbang.Repository.AlarmRepository;
import com.example.bbungeobbang.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AlarmController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlarmRepository alarmRepository;


    @GetMapping("/alarm/{userId}")
    public String showAlarm(@PathVariable String userId, Model model) {
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
        String nickname = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."))
                .getNickname();

        model.addAttribute("nickname", nickname);
        model.addAttribute("alarmContents",alarmRepository.findAlarmContentsByUserId(userId));
        model.addAttribute(userId);
        model.addAttribute("isBaked", alarmRepository.findIsBakedByUserId(userId));
        model.addAttribute("letterId", alarmRepository.findLetterIdByUserId(userId));
        return "alert";
    }
}

