package com.mungdori.sponge.application.pet.provided;

import com.mungdori.sponge.application.pet.PetModifyService;
import com.mungdori.sponge.domain.pet.Pet;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mungdori.sponge.domain.pet.PetFixture.createPetInfoUpdateRequest;
import static com.mungdori.sponge.domain.pet.PetFixture.createPetRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record PetManagerTest(PetModifyService modifyService, EntityManager entityManager)  {


    @Test
    void register() {
        Pet pet = modifyService.register(createPetRegisterRequest());

        assertThat(pet.getId()).isNotNull();
        assertThat(pet.getWeight()).isGreaterThan(0);

    }

    @Test
    void update() {
        Pet pet = modifyService.register(createPetRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        var request = createPetInfoUpdateRequest();
        pet = modifyService.update(pet.getId(),request);

        assertThat(pet.getId()).isNotNull();
        assertThat(pet.getName()).isEqualTo(request.name());

    }

}