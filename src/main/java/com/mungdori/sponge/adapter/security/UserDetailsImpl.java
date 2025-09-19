package com.mungdori.sponge.adapter.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final String nickname;
    
    // 비밀번호를 여기에다가 담아야하는 지는 검증이 필요함
    private final String passwordHash;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String email, String nickname, String passwordHash, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
