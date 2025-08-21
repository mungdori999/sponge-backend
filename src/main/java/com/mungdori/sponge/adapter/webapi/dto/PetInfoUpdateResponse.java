package com.mungdori.sponge.adapter.webapi.dto;


import com.mungdori.sponge.domain.pet.Pet;

public record PetInfoUpdateResponse(
        Long petId,
        String name,
        String breed,
        String gender,
        int age,
        float weight) {

    public static PetInfoUpdateResponse of(Pet pet) {
        return new PetInfoUpdateResponse(pet.getId(), pet.getName(), pet.getBreed(), pet.getGender().name(), pet.getAge(), pet.getWeight());
    }
}
