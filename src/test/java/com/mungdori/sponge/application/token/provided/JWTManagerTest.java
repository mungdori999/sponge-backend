package com.mungdori.sponge.application.token.provided;

import com.mungdori.sponge.domain.token.RefreshToken;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record JWTManagerTest(JWTManager jwtManager, EntityManager entityManager) {

    @Test
    void save() {
        RefreshToken refreshToken = jwtManager.save("mungdori999@gmail.com", "refreshToken");
        entityManager.flush();
        entityManager.clear();

        assertThat(refreshToken.getId()).isNotNull();

    }

    @Test
    void delete() {
        RefreshToken refreshToken = jwtManager.save("mungdori999@gmail.com", "refreshToken");
        entityManager.flush();
        entityManager.clear();

        jwtManager.delete(refreshToken.getRefreshToken());
        entityManager.flush();
        entityManager.clear();

        
    }

}