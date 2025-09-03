package com.mungdori.sponge.adapter.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.application.token.provided.JWTManager;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.Map;

import static com.mungdori.sponge.AssertThatUtils.notNull;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
record LoginFilterTest(MockMvcTester mvcTester, ObjectMapper objectMapper, OwnerManager ownerManager) {

    @Test
    void ownerLogin() throws Exception {
        ownerManager.register(OwnerFixture.createOwnerRegisterRequest());

        Map<String, String> loginRequest = Map.of(
                "email", "mungdori999@gmail.com",
                "password", "longsecret",
                "loginType", "owner"
        );

        MvcTestResult result = mvcTester.post().uri("/login").content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.accessToken", notNull())
                .hasPathSatisfying("$.refreshToken", notNull());

    }

    @Test
    void ownerLoginFail() throws Exception {
        ownerManager.register(OwnerFixture.createOwnerRegisterRequest());

        Map<String, String> loginRequest = Map.of(
                "email", "notfound@gmail.com",
                "password", "longsecret",
                "loginType", "owner"
        );

        MvcTestResult result = mvcTester.post().uri("/login").content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result)
                .hasStatus(HttpStatus.UNAUTHORIZED);

    }

}