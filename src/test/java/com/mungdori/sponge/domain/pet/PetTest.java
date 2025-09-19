package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.owner.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.pet.PetFixture.createPetInfoUpdateRequest;
import static com.mungdori.sponge.domain.pet.PetFixture.createPetRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

class PetTest {

    Pet pet;

    @BeforeEach
    void setUp() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());
        pet = Pet.register(createPetRegisterRequest(), owner.getId());
    }

    @Test
    void registerPet() {
        assertThat(pet.getName()).isNotNull();
        assertThat(pet.getWeight()).isGreaterThan(0);
    }

    @Test
    void updateInfo() {
        var request = createPetInfoUpdateRequest();
        pet.updateInfo(createPetInfoUpdateRequest());

        assertThat(pet.getName()).isEqualTo(request.name());
    }


}