package com.mungdori.sponge.adapter.security.filter;

import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import com.mungdori.sponge.adapter.security.SecurePasswordEncoder;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record LoginAuthenticationProviderTest(LoginAuthenticationProvider provider,
                                       OwnerRepository ownerRepository,
                                       SecurePasswordEncoder passwordEncoder
) {
    @Test
    void authenticateOwner() {

        var request = OwnerFixture.createOwnerRegisterRequest();

        Owner owner = Owner.register(request, passwordEncoder);
        ownerRepository.save(owner);

        LoginTypeAuthenticationToken token =
                new LoginTypeAuthenticationToken(request.email(), request.password(), "OWNER");


        Authentication authentication = provider.authenticate(token);


        assertThat(authentication.isAuthenticated()).isTrue();
    }

    @Test
    void authenticateOwnerEmailFail() {

        var request = OwnerFixture.createOwnerRegisterRequest();

        Owner owner = Owner.register(request, passwordEncoder);
        ownerRepository.save(owner);

        LoginTypeAuthenticationToken token =
                new LoginTypeAuthenticationToken("notfound@naver.com", request.password(), "OWNER");

         Assertions.assertThatThrownBy(()-> provider.authenticate(token))
             .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void authenticateOwnerPasswordFail() {

        var request = OwnerFixture.createOwnerRegisterRequest();

        Owner owner = Owner.register(request, passwordEncoder);
        ownerRepository.save(owner);

        LoginTypeAuthenticationToken token =
                new LoginTypeAuthenticationToken(request.email(), "wrongpw", "OWNER");

        Assertions.assertThatThrownBy(()-> provider.authenticate(token))
                .isInstanceOf(AuthenticationException.class);
    }


}