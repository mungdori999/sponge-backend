package com.mungdori.sponge.adapter.webapi.dto.pet;


import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.shared.GenderType;

public record PetInfoUpdateResponse(
        Long petId,
        String name,
        String breed,
        GenderType gender,
        int age,
        float weight,
        String petImgUrl) {

    public static PetInfoUpdateResponse of(Pet pet) {
        return new PetInfoUpdateResponse(pet.getId(), pet.getName(), pet.getBreed(), pet.getGender(), pet.getAge(), pet.getWeight(), pet.getPetImgUrl());
    }
}
