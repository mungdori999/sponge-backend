package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.security.utils.JWTUtil;
import com.mungdori.sponge.adapter.security.utils.LoginType;
import com.mungdori.sponge.application.token.provided.JWTManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static com.mungdori.sponge.AssertThatUtils.notNull;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class ReissueApiTest {

    final MockMvcTester mvcTester;
    final JWTManager jwtManager;


    @Test
    void reissueOwner() {

        String refreshToken = JWTUtil.createJWT(1L, "nickname", LoginType.OWNER.name(), false);
        jwtManager.save(refreshToken);

        MvcTestResult result = mvcTester.post().uri("/api/reissue")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(refreshToken).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.refreshToken", notNull())
                .hasPathSatisfying("$.accessToken", notNull());

    }
}