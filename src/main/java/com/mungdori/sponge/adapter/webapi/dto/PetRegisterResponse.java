package com.mungdori.sponge.adapter.webapi.dto;


import com.mungdori.sponge.domain.pet.Pet;

public record PetRegisterResponse(
        Long petId,
        String name,
        String breed,
        String gender,
        int age,
        float weight) {

    public static PetRegisterResponse of(Pet pet) {
        return new PetRegisterResponse(pet.getId(), pet.getName(), pet.getBreed(), pet.getGender().name(), pet.getAge(), pet.getWeight());
    }
}
