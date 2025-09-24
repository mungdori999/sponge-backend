package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.shared.GenderType;

public class PetFixture {
    public static PetRegisterRequest createPetRegisterRequest(String name) {

        return new PetRegisterRequest(name, "웰시코기", GenderType.MALE, 22, 1.5f,"img_url.com");
    }

    public static PetRegisterRequest createPetRegisterRequest() {
        return createPetRegisterRequest("바둑이");
    }

    public static PetInfoUpdateRequest createPetInfoUpdateRequest() {

        return new PetInfoUpdateRequest("수정바둑이", "웰시코기", GenderType.MALE, 23, 2.5f,"new_img_url.com");
    }
}
