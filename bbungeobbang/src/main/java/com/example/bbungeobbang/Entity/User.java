package com.example.bbungeobbang.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
@Entity
@Table(name = "User")
public class User implements UserDetails {
    @Id
    @Column(nullable = false)
    private String userId; // 테이블의 Primary Key

    @Column(nullable = false)
    private String pwd; // 비밀번호

    @Column(length = 100, nullable = false, unique = true)
    private String nickname; // 닉네임

    @Column(nullable = false)
    private String role; // 역할 (기본값은 서비스에서 설정)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 예를 들어, 권한을 ROLE_USER로 설정 (상황에 따라 권한을 다르게 설정할 수 있음)
        return List.of(() -> "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
