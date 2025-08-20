package com.mungdori.sponge.application.pet.provided;

import com.mungdori.sponge.domain.pet.Pet;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mungdori.sponge.domain.pet.PetFixture.createPetRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record PetFinderTest(PetFinder petFinder, PetManager petManager, EntityManager entityManager) {

    @Test
    void petFind() {
        Pet pet = petManager.register(createPetRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Pet found = petFinder.find(pet.getId());

        assertThat(found.getId()).isEqualTo(pet.getId());
        assertThat(found.getName()).isEqualTo(pet.getName());
    }

    @Test
    void petFindFail() {
        Assertions.assertThatThrownBy(() -> petFinder.find(999L))
                .isInstanceOf(IllegalArgumentException.class);
    }


}