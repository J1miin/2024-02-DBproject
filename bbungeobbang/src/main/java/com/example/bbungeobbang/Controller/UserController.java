package com.example.bbungeobbang.Controller;

import com.example.bbungeobbang.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    //회원가입페이지 로딩
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "signup";
    }

    //회원가입
    @PostMapping("/register")
    public String registerUser(@RequestParam String userId, @RequestParam String nickname,
                               @RequestParam String pwd, @RequestParam String passwordChk,
                               Model model) {

        // 비밀번호 확인
        if (!pwd.equals(passwordChk)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "signup";
        }

        userService.registerUser(userId, nickname, pwd);
        return "redirect:/login"; // 회원가입 성공 후 로그인 페이지 이동
    }

    // 로그인 폼 렌더링
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String userId, @RequestParam String pwd, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userId, pwd)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/home"; // 로그인 성공 >> home.html이동
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "아이디나 비밀번호가 잘못되었습니다.");
            return "login"; // 로그인 실패 시 다시 로그인 폼으로
        }
    }


}
