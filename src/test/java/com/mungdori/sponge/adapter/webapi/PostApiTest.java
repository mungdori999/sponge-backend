package com.mungdori.sponge.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.adapter.security.config.WithMockOwner;
import com.mungdori.sponge.application.pet.provided.PetManager;
import com.mungdori.sponge.application.post.provided.PostManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetFixture;
import com.mungdori.sponge.domain.pet.PetInfoUpdateRequest;
import com.mungdori.sponge.domain.post.PostCreateRequest;
import com.mungdori.sponge.domain.post.PostFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.util.Objects;

import static com.mungdori.sponge.AssertThatUtils.equalsTo;
import static com.mungdori.sponge.AssertThatUtils.notNull;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static java.util.Objects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class PostApiTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;
    final EntityManager entityManager;
    final PostManager postManager;


    @Test
    @WithMockOwner(email = "mungdori999@gmail.com")
    void create() throws JsonProcessingException {
        Owner owner = createOwner();
        Pet pet = createPet(owner.getId());
        PostCreateRequest request = PostFixture.createPostCreateRequest();

        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/post").param("petId", requireNonNull(pet.getId()).toString()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.postId", notNull());
    }

    Owner createOwner() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());

        entityManager.persist(owner);

        entityManager.flush();
        entityManager.clear();

        return owner;
    }

    Pet createPet(Long ownerId) {
        Pet pet = Pet.register(PetFixture.createPetRegisterRequest(), ownerId);

        entityManager.persist(pet);

        entityManager.flush();
        entityManager.clear();

        return pet;
    }


}