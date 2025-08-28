package com.mungdori.sponge.application.pet.required;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.pet.PetFixture.createPetRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
record PetRepositoryTest(PetRepository petRepository, EntityManager entityManager) {

    @Test
    void createPet() {
        Owner owner = createOwner();
        Pet pet = Pet.register(createPetRegisterRequest(), owner);

        assertThat(pet.getId()).isNull();

        petRepository.save(pet);

        assertThat(pet.getId()).isNotNull();

    }

    Owner createOwner() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());

        entityManager.persist(owner);

        entityManager.flush();
        entityManager.clear();

        return owner;
    }

}