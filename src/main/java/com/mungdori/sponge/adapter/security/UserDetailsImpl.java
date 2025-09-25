package com.mungdori.sponge.adapter.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final String nickname;
    
    // 비밀번호를 여기에다가 담아야하는 지는 검증이 필요함
    private final String passwordHash;


    public UserDetailsImpl(String email, String nickname, String passwordHash) {
        this.email = email;
        this.nickname = nickname;
        this.passwordHash = passwordHash;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return nickname;
    }
}
