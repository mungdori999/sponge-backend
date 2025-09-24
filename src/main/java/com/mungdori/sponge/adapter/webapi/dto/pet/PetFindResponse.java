package com.mungdori.sponge.adapter.webapi.dto.pet;

import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.shared.GenderType;

public record PetFindResponse(
        Long petId,
        String name,
        String breed,
        GenderType gender,
        int age,
        float weight,
        String petImgUrl
) {
    public static PetFindResponse of(Pet pet) {
        return new PetFindResponse(pet.getId(), pet.getName(), pet.getBreed(), pet.getGender(), pet.getAge(), pet.getWeight(), pet.getPetImgUrl());
    }
}
