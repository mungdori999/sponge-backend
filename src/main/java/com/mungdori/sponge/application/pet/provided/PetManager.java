package com.mungdori.sponge.application.pet.provided;

import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetInfoUpdateRequest;
import com.mungdori.sponge.domain.pet.PetRegisterRequest;
import jakarta.validation.Valid;

public interface PetManager {

    Pet register(@Valid PetRegisterRequest registerRequest, Long ownerId);

    Pet update(Long petId, @Valid PetInfoUpdateRequest updateRequest,Long ownerId);
}
