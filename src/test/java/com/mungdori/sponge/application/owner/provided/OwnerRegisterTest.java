package com.mungdori.sponge.application.owner.provided;

import com.mungdori.sponge.domain.owner.DuplicateNicknameException;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record OwnerRegisterTest(OwnerRegister ownerRegister, EntityManager entityManager) {

    @Test
    void register() {
        Owner owner = ownerRegister.register(createOwnerRegisterRequest());

        assertThat(owner.getId()).isNotNull();
        assertThat(owner.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void duplicateNicknameFail() {
        ownerRegister.register(createOwnerRegisterRequest());

        Assertions.assertThatThrownBy(() -> ownerRegister.register(createOwnerRegisterRequest()))
                .isInstanceOf(DuplicateNicknameException.class);
    }
}