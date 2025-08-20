package com.mungdori.sponge.application.pet.provided;

import com.mungdori.sponge.domain.pet.Pet;

public interface PetFinder {

    Pet find (Long petId);
}
