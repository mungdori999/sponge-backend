package com.mungdori.sponge.application.token.required;

import com.mungdori.sponge.domain.token.RefreshToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
record RefreshTokenRepositoryTest(RefreshTokenRepository refreshTokenRepository) {

    @Test
    void save() {
        RefreshToken refreshToken = RefreshToken.create("refreshToken");

        assertThat(refreshToken.getId()).isNotNull();

        refreshTokenRepository.save(refreshToken);


        var found = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken()).orElseThrow();

        assertThat(found.getId()).isEqualTo(refreshToken.getId());
        assertThat(found.getRefreshToken()).isEqualTo(refreshToken.getRefreshToken());

    }

    @Test
    void deleteToken() {
        RefreshToken refreshToken = RefreshToken.create("refreshToken");

        refreshTokenRepository.save(refreshToken);

        refreshTokenRepository.delete(refreshToken);

        Optional<RefreshToken> found = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken());
        assertThat(found).isEmpty();

    }
}