package com.mungdori.sponge.application.owner.provided;

import com.mungdori.sponge.domain.owner.DuplicateEmailException;
import com.mungdori.sponge.domain.owner.DuplicateNicknameException;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerInfoUpdateRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
record OwnerManagerTest(OwnerManager ownerManager, EntityManager entityManager) {

    @Test
    void register() {
        Owner owner = registerOwner();

        assertThat(owner.getId()).isNotNull();
        assertThat(owner.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }


    @Test
    void duplicateNicknameFail() {
        ownerManager.register(createOwnerRegisterRequest());

        assertThatThrownBy(() -> ownerManager.register(createOwnerRegisterRequest("newemail@naver.com","nickname")))
                .isInstanceOf(DuplicateNicknameException.class);
    }

    @Test
    void duplicateEmailFail() {
        ownerManager.register(createOwnerRegisterRequest());

        assertThatThrownBy(() -> ownerManager.register(createOwnerRegisterRequest("mungdori999@gmail.com","nickname1")))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void updateOwnerInfo() {
        Owner owner = registerOwner();

        owner = ownerManager.update(owner.getId(), createOwnerInfoUpdateRequest("newname"));

        assertThat(owner.getNickname()).isEqualTo("newname");
    }


    @Test
    void updateOwnerInfoFail() {
        Owner owner = registerOwner();

        ownerManager.update(owner.getId(), createOwnerInfoUpdateRequest("newname"));

        // 중복 닉네임
        assertThatThrownBy(() -> ownerManager.update(owner.getId(), createOwnerInfoUpdateRequest("newname")))
                .isInstanceOf(DuplicateNicknameException.class);

        // 너무 긴 닉네임
        assertThatThrownBy(() -> ownerManager.update(owner.getId(),
                createOwnerInfoUpdateRequest("longnickname")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    private Owner registerOwner() {
        Owner owner = ownerManager.register(createOwnerRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return owner;
    }


}