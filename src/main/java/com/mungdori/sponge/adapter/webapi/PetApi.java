package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.webapi.dto.pet.PetInfoUpdateResponse;
import com.mungdori.sponge.adapter.webapi.dto.pet.PetRegisterResponse;
import com.mungdori.sponge.application.pet.provided.PetManager;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetInfoUpdateRequest;
import com.mungdori.sponge.domain.pet.PetRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PetApi {

    private final PetManager petManager;


    @PostMapping("/api/pet")
    public PetRegisterResponse register(@RequestBody @Valid PetRegisterRequest petRegisterRequest, @RequestParam Long ownerId) {
        Pet pet = petManager.register(petRegisterRequest,ownerId);
        return PetRegisterResponse.of(pet);
    }

    @PatchMapping("/api/pet/{petId}")
    public PetInfoUpdateResponse update(@PathVariable Long petId, @RequestBody @Valid PetInfoUpdateRequest petInfoUpdateRequest) {
        Pet pet = petManager.update(petId, petInfoUpdateRequest);
        return PetInfoUpdateResponse.of(pet);
    }
}
