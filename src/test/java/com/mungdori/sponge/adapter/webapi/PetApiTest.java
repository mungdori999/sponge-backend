package com.mungdori.sponge.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.domain.pet.PetFixture;
import com.mungdori.sponge.domain.pet.PetRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static com.mungdori.sponge.AssertThatUtils.notNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class PetApiTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;

    @Test
    void register() throws JsonProcessingException {
        PetRegisterRequest request = PetFixture.createPetRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/pet").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.petId", notNull());
    }

    @Test
    void registerValidFail() throws JsonProcessingException {
        PetRegisterRequest request = PetFixture.createPetRegisterRequest("바");
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/pet").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .apply(print())
                .hasStatus(HttpStatus.BAD_REQUEST);
    }
}