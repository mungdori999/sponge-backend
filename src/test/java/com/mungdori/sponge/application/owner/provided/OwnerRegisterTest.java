package com.mungdori.sponge.application.owner.provided;

import com.mungdori.sponge.domain.owner.DuplicateNicknameException;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerInfoUpdateRequest;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
record OwnerRegisterTest(OwnerRegister ownerRegister, EntityManager entityManager) {

    @Test
    void register() {
        Owner owner = registerOwner();

        assertThat(owner.getId()).isNotNull();
        assertThat(owner.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }


    @Test
    void duplicateNicknameFail() {
        ownerRegister.register(createOwnerRegisterRequest());

        assertThatThrownBy(() -> ownerRegister.register(createOwnerRegisterRequest()))
                .isInstanceOf(DuplicateNicknameException.class);
    }

    @Test
    void updateOwnerInfo() {
        Owner owner = registerOwner();

        owner = ownerRegister.update(owner.getId(), new OwnerInfoUpdateRequest("testname"));

        assertThat(owner.getNickname()).isEqualTo("testname");
    }

    @Test
    void updateOwnerInfoFail() {
        Owner owner = registerOwner();

        ownerRegister.update(owner.getId(), new OwnerInfoUpdateRequest("testname"));

        // 중복 닉네임
        assertThatThrownBy(() -> ownerRegister.update(owner.getId(), new OwnerInfoUpdateRequest("testname")))
                .isInstanceOf(DuplicateNicknameException.class);

        // 너무 긴 닉네임
        assertThatThrownBy(() -> ownerRegister.update(owner.getId(), new OwnerInfoUpdateRequest("longtestname")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    private Owner registerOwner() {
        Owner owner = ownerRegister.register(createOwnerRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return owner;
    }
}