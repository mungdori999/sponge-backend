package com.mungdori.sponge.adapter.security.utils;

import com.mungdori.sponge.adapter.security.config.WithMockOwner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
record AuthorizationUtilTest(AuthorizationUtil authorizationUtil) {


    @Test
    @WithMockOwner
    void authorizationId() {
        Long id = authorizationUtil.getId();


        assertThat(id).isEqualTo(1L);
    }
}