package com.mungdori.sponge.application.pet.provided;

import com.mungdori.sponge.domain.pet.Pet;

import java.util.List;

public interface PetFinder {

    Pet find (Long petId);

    List<Pet> findList(Long ownerId);
}
