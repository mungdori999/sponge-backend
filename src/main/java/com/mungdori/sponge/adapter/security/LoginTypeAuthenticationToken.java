package com.mungdori.sponge.adapter.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class LoginTypeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String loginType;

    public LoginTypeAuthenticationToken(Object principal, Object credentials,String loginType) {
        super(principal, credentials);
        this.loginType = loginType;
    }

}
