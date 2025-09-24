package com.mungdori.sponge.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.adapter.security.config.WithMockOwner;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerFixture;
import com.mungdori.sponge.domain.owner.OwnerInfoUpdateRequest;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static com.mungdori.sponge.AssertThatUtils.equalsTo;
import static com.mungdori.sponge.AssertThatUtils.notNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class OwnerApiTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;
    final OwnerManager ownerManager;

    @Test
    void getOwner()  {
        Owner owner = ownerManager.register(OwnerFixture.createOwnerRegisterRequest());

        MvcTestResult result = mvcTester.get().uri("/api/owner/{id}", owner.getId())
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.ownerId", notNull())
                .hasPathSatisfying("$.nickname", equalsTo(owner));
    }

    @Test
    @WithMockOwner
    void getMyInfo()  {
        Owner owner = ownerManager.register(OwnerFixture.createOwnerRegisterRequest("test@mail.com","nickname"));

        MvcTestResult result = mvcTester.get().uri("/api/owner")
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.ownerId", notNull())
                .hasPathSatisfying("$.nickname", equalsTo(owner));
    }

    @Test
    void register() throws JsonProcessingException {
        OwnerRegisterRequest request = OwnerFixture.createOwnerRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/owner").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.ownerId", notNull())
                .hasPathSatisfying("$.email", equalsTo(request));
    }

    @Test
    @WithMockOwner
    void update() throws JsonProcessingException {
        Owner owner = ownerManager.register(OwnerFixture.createOwnerRegisterRequest());

        OwnerInfoUpdateRequest request = OwnerFixture.createOwnerInfoUpdateRequest("newnick");
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.patch().uri("/api/owner/{id}", owner.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.ownerId", notNull())
                .hasPathSatisfying("$.nickname", equalsTo(request));
    }


    @Test
    void duplicateEmail() throws JsonProcessingException {
        ownerManager.register(OwnerFixture.createOwnerRegisterRequest());

        OwnerRegisterRequest request = OwnerFixture.createOwnerRegisterRequest("mungdori999@gmail.com", "newnick");
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/api/owner").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson).exchange();

        assertThat(result)
                .apply(print())
                .hasStatus(HttpStatus.CONFLICT);

    }

}