package com.mungdori.sponge.adapter.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class LoginFilterTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;
    final OwnerManager ownerManager;

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

                assertThat(result).hasStatusOk();
    }

}