package com.mungdori.sponge.domain.token;

import com.mungdori.sponge.domain.shared.Email;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    @Test
    void create() {
        RefreshToken token = RefreshToken.create(new Email("mungdori999@gmail.com"), "refreshToken");

        assertThat(token.getEmail().address()).isNotNull();
        assertThat(token.getCreatedDate()).isNotNull();
    }

}