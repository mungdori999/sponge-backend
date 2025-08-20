package com.mungdori.sponge.application.pet;

import com.mungdori.sponge.application.pet.provided.PetFinder;
import com.mungdori.sponge.application.pet.provided.PetManager;
import com.mungdori.sponge.application.pet.required.PetRepository;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetInfoUpdateRequest;
import com.mungdori.sponge.domain.pet.PetRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class PetModifyService implements PetManager {

    private final PetRepository petRepository;
    private final PetFinder petFinder;

    @Override
    public Pet register(PetRegisterRequest registerRequest) {
        Pet pet = Pet.register(registerRequest);

        pet = petRepository.save(pet);

        return pet;
    }

    @Override
    public Pet update(Long petId, PetInfoUpdateRequest updateRequest) {
        Pet pet = petFinder.find(petId);

        pet.updateInfo(updateRequest);

        pet = petRepository.save(pet);

        return pet;
    }
}
