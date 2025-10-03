package com.mungdori.sponge.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.adapter.security.config.WithMockOwner;
import com.mungdori.sponge.application.pet.provided.PetManager;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetFixture;
import com.mungdori.sponge.domain.pet.PetInfoUpdateRequest;
import com.mungdori.sponge.domain.pet.PetRegisterRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.Objects;

import static com.mungdori.sponge.AssertThatUtils.equalsTo;
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
    final EntityManager entityManager;
    final PetManager petManager;

    @Test
    @WithMockOwner
    void register() throws JsonProcessingException {
        Long ownerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PetRegisterRequest request = PetFixture.createPetRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/pet").param("ownerId", Objects.requireNonNull(ownerId).toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.petId", notNull())
                .hasPathSatisfying("$.petImgUrl", notNull());
    }

    @Test
    @WithMockOwner()
    void update() throws JsonProcessingException {
        Long ownerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pet pet = petManager.register(PetFixture.createPetRegisterRequest(), ownerId);

        PetInfoUpdateRequest request = PetFixture.createPetInfoUpdateRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.patch().uri("/api/pet/{id}", pet.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.petId", notNull())
                .hasPathSatisfying("$.name", equalsTo(request));
    }

    @Test
    @WithMockOwner()
    void registerValidFail() throws JsonProcessingException {
        PetRegisterRequest request = PetFixture.createPetRegisterRequest("바");
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/pet").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .apply(print())
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @WithMockOwner()
    void updateFail() throws JsonProcessingException {
        Long ownerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pet pet = petManager.register(PetFixture.createPetRegisterRequest(), ownerId);

        PetInfoUpdateRequest request = PetFixture.createPetInfoUpdateRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.patch().uri("/api/pet/{id}", pet.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .apply(print())
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

}