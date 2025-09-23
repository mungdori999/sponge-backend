package com.mungdori.sponge.application.pet;

import com.mungdori.sponge.application.owner.provided.OwnerFinder;
import com.mungdori.sponge.application.pet.provided.PetFinder;
import com.mungdori.sponge.application.pet.provided.PetManager;
import com.mungdori.sponge.application.pet.required.PetRepository;
import com.mungdori.sponge.domain.owner.Owner;
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
    private final OwnerFinder ownerFinder;

    @Override
    public Pet register(PetRegisterRequest registerRequest, Long ownerId) {
        Owner owner = ownerFinder.findById(ownerId);

        Pet pet = Pet.register(registerRequest, owner.getId());

        pet = petRepository.save(pet);

        return pet;
    }

    @Override
    public Pet update(Long petId, PetInfoUpdateRequest updateRequest, String email) {
        Pet pet = petFinder.find(petId);

        Owner owner = ownerFinder.findByEmail(email);

        checkValidMyAccount(pet, owner);

        pet.updateInfo(updateRequest);

        pet = petRepository.save(pet);

        return pet;
    }

    private static void checkValidMyAccount(Pet pet, Owner owner) {
        if(!pet.getOwnerId().equals(owner.getId())) {
            throw new IllegalArgumentException("소유한 반려견이 아닙니다!");
        }
    }
}
