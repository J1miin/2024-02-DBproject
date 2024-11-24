package com.example.bbungeobbang.Service;

import com.example.bbungeobbang.Entity.User;
import com.example.bbungeobbang.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void registerUser(String userId, String username, String password) {
        // User 객체 생성
        User user = new User();
        user.setUserId(userId);
        user.setNickname(username);
        user.setPwd(password);

        // 비밀번호 암호화
        user.setPwd(passwordEncoder.encode(user.getPwd()));

        // 기본 역할 설정
        user.setRole("USER");

        // 중복 체크
        if (userRepository.existsById(user.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        if (userRepository.existsByNickname(user.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        userRepository.save(user); // 사용자 저장
    }
}
