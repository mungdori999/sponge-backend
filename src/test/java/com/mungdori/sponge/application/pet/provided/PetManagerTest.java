package com.mungdori.sponge.application.pet.provided;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.pet.PetFixture.createPetInfoUpdateRequest;
import static com.mungdori.sponge.domain.pet.PetFixture.createPetRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record PetManagerTest(PetManager petManager, EntityManager entityManager)  {


    @Test
    void register() {
        Owner owner = createOwner();

        Pet pet = petManager.register(createPetRegisterRequest(), owner.getId());

        assertThat(pet.getId()).isNotNull();
        assertThat(pet.getWeight()).isGreaterThan(0);
        assertThat(pet.getOwnerId()).isEqualTo(owner.getId());

    }

    @Test
    void update() {
        Owner owner = createOwner();

        Pet pet = petManager.register(createPetRegisterRequest(), owner.getId());
        entityManager.flush();
        entityManager.clear();

        var request = createPetInfoUpdateRequest();
        pet = petManager.update(pet.getId(),request,owner.getEmail().address());

        assertThat(pet.getId()).isNotNull();
        assertThat(pet.getName()).isEqualTo(request.name());


    }

    @Test
    void updateFail() {
        Owner owner = createOwner();

        Pet pet = petManager.register(createPetRegisterRequest(), owner.getId());
        entityManager.flush();
        entityManager.clear();

        var request = createPetInfoUpdateRequest();

         Assertions.assertThatThrownBy(()-> petManager.update(pet.getId(),request,"unvalidEmail@naver.com"))
             .isInstanceOf(IllegalArgumentException.class);
    }

    Owner createOwner() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());

        entityManager.persist(owner);

        entityManager.flush();
        entityManager.clear();

        return owner;
    }


}