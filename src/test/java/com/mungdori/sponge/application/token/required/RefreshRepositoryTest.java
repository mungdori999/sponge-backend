package com.mungdori.sponge.application.token.required;

import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.token.RefreshToken;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
record RefreshRepositoryTest (RefreshRepository refreshRepository, EntityManager entityManager) {

    @Test
    void save() {
        RefreshToken refreshToken = RefreshToken.create(new Email("mungdori999@gmail.com"), "refreshToken");

        assertThat(refreshToken.getId()).isNull();

        refreshRepository.save(refreshToken);

        assertThat(refreshToken.getId()).isNotNull();

    }
}