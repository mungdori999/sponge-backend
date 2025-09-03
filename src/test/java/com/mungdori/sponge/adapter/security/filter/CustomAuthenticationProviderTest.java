package com.mungdori.sponge.adapter.security.filter;

import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import com.mungdori.sponge.adapter.security.SecurePasswordEncoder;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
record CustomAuthenticationProviderTest(CustomAuthenticationProvider provider,
                                        OwnerRepository ownerRepository,
                                        SecurePasswordEncoder passwordEncoder
) {
    @Test
    void authenticate_owner() {

        var request = OwnerFixture.createOwnerRegisterRequest();

        Owner owner = Owner.register(request, passwordEncoder);
        ownerRepository.save(owner);

        LoginTypeAuthenticationToken token =
                new LoginTypeAuthenticationToken(request.email(), request.password(), "OWNER");


        Authentication authentication = provider.authenticate(token);


        assertThat(authentication.isAuthenticated()).isTrue();
    }

    @Test
    void authenticate_owner_fail() {

        var request = OwnerFixture.createOwnerRegisterRequest();

        Owner owner = Owner.register(request, passwordEncoder);
        ownerRepository.save(owner);

        LoginTypeAuthenticationToken token =
                new LoginTypeAuthenticationToken("notfound@naver.com", request.password(), "OWNER");

         Assertions.assertThatThrownBy(()-> provider.authenticate(token))
             .isInstanceOf(AuthenticationException.class);
    }


}