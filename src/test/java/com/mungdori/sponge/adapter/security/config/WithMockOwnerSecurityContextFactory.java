package com.mungdori.sponge.adapter.security.config;


import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import com.mungdori.sponge.adapter.security.utils.LoginType;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WithMockOwnerSecurityContextFactory implements WithSecurityContextFactory<WithMockOwner> {

    @Autowired
    private OwnerManager ownerManager;

    @Override
    public SecurityContext createSecurityContext(WithMockOwner annotation) {
        Owner owner = ownerManager.register(OwnerFixture.createOwnerRegisterRequest());

        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final LoginTypeAuthenticationToken authenticationToken
                = new LoginTypeAuthenticationToken(owner.getId(), null, List.of(new SimpleGrantedAuthority("USER")), LoginType.OWNER.name());
        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
