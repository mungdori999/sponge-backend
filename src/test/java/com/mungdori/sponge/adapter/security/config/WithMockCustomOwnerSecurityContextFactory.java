package com.mungdori.sponge.adapter.security.config;


import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockCustomOwnerSecurityContextFactory implements WithSecurityContextFactory<WithMockOwner> {

    @Override
    public SecurityContext createSecurityContext(WithMockOwner annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final LoginTypeAuthenticationToken authenticationToken
                = new LoginTypeAuthenticationToken(annotation.id(), null, List.of(new SimpleGrantedAuthority("USER")), annotation.loginType());
        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
