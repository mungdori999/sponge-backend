package com.mungdori.sponge.application.owner.required;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
record OwnerRepositoryTest(OwnerRepository ownerRepository, EntityManager entityManager) {

    @Test
    void createOwner() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());

        assertThat(owner.getId()).isNull();

        ownerRepository.save(owner);

        assertThat(owner.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var found = ownerRepository.findById(owner.getId()).orElseThrow();

        assertThat(found.getNickname()).isEqualTo(owner.getNickname());
        assertThat(found.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }


    @Test
    void duplicateEmail() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());
        ownerRepository.save(owner);

        Owner owner2 = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> ownerRepository.save(owner2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}