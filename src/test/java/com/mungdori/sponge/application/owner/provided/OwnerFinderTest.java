package com.mungdori.sponge.application.owner.provided;

import com.mungdori.sponge.domain.owner.Owner;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record OwnerFinderTest(OwnerFinder ownerFinder, OwnerManager ownerManager, EntityManager entityManager) {


    @Test
    void ownerFindById() {
        Owner owner = ownerManager.register(createOwnerRegisterRequest());

        entityManager.flush();
        entityManager.clear();

        Owner found = ownerFinder.findById(owner.getId());
        assertThat(found.getId()).isEqualTo(owner.getId());

    }

    @Test
    void ownerFindByEmail() {
        Owner owner = ownerManager.register(createOwnerRegisterRequest());

        entityManager.flush();
        entityManager.clear();

        Owner found = ownerFinder.findByEmail(owner.getEmail().address());
        assertThat(found.getId()).isEqualTo(owner.getId());

    }

    @Test
    void ownerFindByIdFail() {
        Assertions.assertThatThrownBy(()-> ownerFinder.findById(999L))
            .isInstanceOf(IllegalArgumentException.class);
    }

}