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
    void ownerFind() {
        Owner owner = ownerManager.register(createOwnerRegisterRequest());

        entityManager.flush();
        entityManager.clear();

        Owner found = ownerFinder.find(owner.getId());
        assertThat(found.getId()).isEqualTo(owner.getId());
    }

    @Test
    void ownerFindFail() {
        Assertions.assertThatThrownBy(()-> ownerFinder.find(999L))
            .isInstanceOf(IllegalArgumentException.class);
    }

}