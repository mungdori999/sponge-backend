package com.mungdori.sponge.adapter.security.config;


import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import com.mungdori.sponge.adapter.security.utils.LoginType;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WithMockOwnerSecurityContextFactory implements WithSecurityContextFactory<WithMockOwner> {

    @Autowired
    private OwnerManager ownerManager;

    @Override
    public SecurityContext createSecurityContext(WithMockOwner annotation) {
        // 1. 테스트용 Owner 등록
        Owner owner = ownerManager.register(OwnerFixture.createOwnerRegisterRequest(UUID.randomUUID() + "@mail.com",UUID.randomUUID().toString().replace("-", "").substring(0, 10)));

        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final LoginTypeAuthenticationToken authenticationToken
                = new LoginTypeAuthenticationToken(owner.getId(), null, List.of(new SimpleGrantedAuthority("USER")), LoginType.OWNER.name());
        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
