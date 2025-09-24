package com.mungdori.sponge.application.pet.required;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.pet.PetFixture.createPetRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
record PetRepositoryTest(PetRepository petRepository, EntityManager entityManager) {

    @Test
    void createPet() {
        Owner owner = createOwner();
        Pet pet = Pet.register(createPetRegisterRequest(), owner.getId());

        assertThat(pet.getId()).isNull();

        petRepository.save(pet);

        assertThat(pet.getId()).isNotNull();

    }

    @Test
    void findByOwnerId() {
        Owner owner = createOwner();

        for (int i = 0; i < 10; i++) {
            Pet pet = Pet.register(createPetRegisterRequest(), owner.getId());
            petRepository.save(pet);
        }

        List<Pet> petList = petRepository.findByOwnerId(owner.getId());

        assertThat(petList).hasSize(10);

    }

    Owner createOwner() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());

        entityManager.persist(owner);

        entityManager.flush();
        entityManager.clear();

        return owner;
    }

}