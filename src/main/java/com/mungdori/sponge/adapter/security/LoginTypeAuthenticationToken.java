package com.mungdori.sponge.adapter.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class LoginTypeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String loginType;

    public LoginTypeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String loginType) {
        super(principal, credentials, authorities);
        this.loginType = loginType;
    }

    public LoginTypeAuthenticationToken(Object principal, Object credentials, String loginType) {
        super(principal, credentials);
        this.loginType = loginType;
    }

}
