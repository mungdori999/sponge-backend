package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.shared.GenderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.pet.PetFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class PetTest {

    Pet pet;

    @BeforeEach
    void setUp() {
        pet = Pet.register(createPetRegisterRequest());
    }

    @Test
    void registerPet() {
        assertThat(pet.getName()).isNotNull();
        assertThat(pet.getGender()).isEqualTo(GenderType.MALE);
    }

}