package com.mungdori.sponge.adapter.webapi.dto.pet;


import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.shared.GenderType;

public record PetRegisterResponse(
        Long petId,
        String name,
        String breed,
        GenderType gender,
        int age,
        float weight) {

    public static PetRegisterResponse of(Pet pet) {
        return new PetRegisterResponse(pet.getId(), pet.getName(), pet.getBreed(), pet.getGender(), pet.getAge(), pet.getWeight());
    }
}
