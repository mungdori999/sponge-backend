package com.mungdori.sponge.application.owner;

import com.mungdori.sponge.application.owner.provided.OwnerRegister;
import com.mungdori.sponge.domain.owner.Owner;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.mungdori.sponge.domain.owner.OwnerFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record OwnerModifyServiceTest(OwnerRegister ownerRegister, EntityManager entityManager) {

    @Test
    void register() {
        Owner owner = ownerRegister.register(createOwnerRegisterRequest());

        assertThat(owner.getId()).isNotNull();
    }
}