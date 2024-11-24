package com.example.bbungeobbang.Service;

import com.example.bbungeobbang.Entity.User;
import com.example.bbungeobbang.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 사용자 정보 가져오기
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // User 객체를 UserDetails 객체로 변환하여 반환
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPwd(), user.getAuthorities());
    }
}
