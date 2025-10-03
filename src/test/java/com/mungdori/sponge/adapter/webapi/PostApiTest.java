package com.mungdori.sponge.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.adapter.security.config.WithMockOwner;
import com.mungdori.sponge.application.post.provided.PostManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetFixture;
import com.mungdori.sponge.domain.post.PostCreateRequest;
import com.mungdori.sponge.domain.post.PostFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static com.mungdori.sponge.AssertThatUtils.notNull;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

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
    @WithMockOwner
    void create() throws JsonProcessingException {
        Long ownerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pet pet = createPet(ownerId);
        PostCreateRequest request = PostFixture.createPostCreateRequest();

        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/post").param("petId", requireNonNull(pet.getId()).toString()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.postId", notNull());
    }

    Pet createPet(Long ownerId) {
        Pet pet = Pet.register(PetFixture.createPetRegisterRequest(), ownerId);

        entityManager.persist(pet);

        entityManager.flush();
        entityManager.clear();

        return pet;
    }


}