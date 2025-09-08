package com.mungdori.sponge.application.token.required;

import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.token.RefreshToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
record RefreshRepositoryTest(RefreshRepository refreshRepository, EntityManager entityManager) {

    @Test
    void save() {
        RefreshToken refreshToken = RefreshToken.create(new Email("mungdori999@gmail.com"), "refreshToken");

        assertThat(refreshToken.getId()).isNull();

        refreshRepository.save(refreshToken);

        assertThat(refreshToken.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var found = refreshRepository.findByRefresh(refreshToken.getRefresh()).orElseThrow();

        assertThat(found.getId()).isEqualTo(refreshToken.getId());
        assertThat(found.getRefresh()).isEqualTo(refreshToken.getRefresh());

    }

    @Test
    void deleteToken() {
        RefreshToken refreshToken = RefreshToken.create(new Email("mungdori999@gmail.com"), "refreshToken");

        refreshRepository.save(refreshToken);
        entityManager.flush();
        entityManager.clear();

        refreshRepository.deleteByRefresh(refreshToken.getRefresh());

        Optional<RefreshToken> found = refreshRepository.findByRefresh(refreshToken.getRefresh());
        assertThat(found).isEmpty();

    }
}