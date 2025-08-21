package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.webapi.dto.PetRegisterResponse;
import com.mungdori.sponge.application.pet.provided.PetManager;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PetApi {

    private final PetManager petManager;


    @PostMapping("/api/pet")
    public PetRegisterResponse register(@RequestBody @Valid PetRegisterRequest petRegisterRequest) {
        Pet pet = petManager.register(petRegisterRequest);
        return PetRegisterResponse.of(pet);
    }
}
