package com.mungdori.sponge.adapter.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.application.trainer.provided.TrainerManager;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import com.mungdori.sponge.domain.trainer.TrainerFixture;
import jakarta.transaction.Transactional;
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
record LoginFilterTest(MockMvcTester mvcTester, ObjectMapper objectMapper, OwnerManager ownerManager,
                       TrainerManager trainerManager) {

    @Test
    void ownerLogin() throws Exception {
        var request = OwnerFixture.createOwnerRegisterRequest();
        ownerManager.register(request);

        Map<String, String> loginRequest = Map.of(
                "email", request.email(),
                "password", request.password(),
                "loginType", "owner"
        );

        MvcTestResult result = mvcTester.post().uri("/api/login").content(objectMapper.writeValueAsString(loginRequest))
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
        var request = OwnerFixture.createOwnerRegisterRequest();
        ownerManager.register(request);

        Map<String, String> loginRequest = Map.of(
                "email", "notfound@naver.com",
                "password", request.password(),
                "loginType", "owner"
        );

        MvcTestResult result = mvcTester.post().uri("/api/login").content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result)
                .hasStatus(HttpStatus.UNAUTHORIZED);

    }

    @Test
    void trainerLogin() throws Exception {
        var request = TrainerFixture.createTrainerRegisterRequest();
        trainerManager.register(request);

        Map<String, String> loginRequest = Map.of(
                "email", request.email(),
                "password", request.password(),
                "loginType", "trainer"
        );

        MvcTestResult result = mvcTester.post().uri("/api/login").content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.accessToken", notNull())
                .hasPathSatisfying("$.refreshToken", notNull());

    }

    @Test
    void trainerLoginFail() throws Exception {
        var request = TrainerFixture.createTrainerRegisterRequest();
        trainerManager.register(request);

        Map<String, String> loginRequest = Map.of(
                "email", request.email(),
                "password", "wrongpassword",
                "loginType", "trainer"
        );

        MvcTestResult result = mvcTester.post().uri("/api/login").content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        assertThat(result)
                .hasStatus(HttpStatus.UNAUTHORIZED);

    }


}