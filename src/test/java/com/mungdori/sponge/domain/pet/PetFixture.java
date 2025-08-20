package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.shared.GenderType;

public class PetFixture {
    public static PetRegisterRequest createPetRegisterRequest() {

        return new PetRegisterRequest("바둑이", "웰시코기", GenderType.MALE, 22, 1.5f);
    }
}
