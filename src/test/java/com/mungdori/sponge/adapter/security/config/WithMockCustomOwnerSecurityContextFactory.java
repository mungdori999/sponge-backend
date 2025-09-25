package com.mungdori.sponge.adapter.security.config;


import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import com.mungdori.sponge.adapter.security.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

import static com.mungdori.sponge.adapter.security.utils.LoginType.OWNER;

public class WithMockCustomOwnerSecurityContextFactory implements WithSecurityContextFactory<WithMockOwner> {

    @Override
    public SecurityContext createSecurityContext(WithMockOwner annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final LoginTypeAuthenticationToken authenticationToken
                = new LoginTypeAuthenticationToken(annotation.email(), null, List.of(new SimpleGrantedAuthority("USER")), annotation.loginType());
        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
