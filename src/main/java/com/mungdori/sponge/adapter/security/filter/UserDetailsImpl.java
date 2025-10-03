package com.mungdori.sponge.adapter.security.filter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String nickname;

    private final String passwordHash;


    public UserDetailsImpl(Long id, String nickname, String passwordHash) {
        this.id = id;

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

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return nickname;
    }
}
