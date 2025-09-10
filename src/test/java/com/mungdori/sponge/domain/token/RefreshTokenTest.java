package com.mungdori.sponge.domain.token;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    @Test
    void create() {
        RefreshToken token = RefreshToken.create("refreshToken");

        assertThat(token.getId()).isNotNull();
        assertThat(token.getRefreshToken()).isEqualTo("refreshToken");
    }

}