package com.mungdori.sponge.adapter.security.utils;

import com.mungdori.sponge.adapter.security.config.WithMockOwner;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
record AuthorizationUtilTest(AuthorizationUtil authorizationUtil) {


    @Test
    @WithMockOwner
    void authorizationId() {
        Long ownerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = authorizationUtil.getId();
        assertThat(id).isEqualTo(ownerId);
    }
}