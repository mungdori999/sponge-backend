package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.security.utils.AuthorizationUtil;
import com.mungdori.sponge.adapter.webapi.dto.pet.PetFindResponse;
import com.mungdori.sponge.adapter.webapi.dto.pet.PetInfoUpdateResponse;
import com.mungdori.sponge.adapter.webapi.dto.pet.PetRegisterResponse;
import com.mungdori.sponge.application.pet.provided.PetFinder;
import com.mungdori.sponge.application.pet.provided.PetManager;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetInfoUpdateRequest;
import com.mungdori.sponge.domain.pet.PetRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetApi {

    private final PetManager petManager;
    private final PetFinder petFinder;
    private final AuthorizationUtil authorizationUtil;


    @GetMapping()
    public List<PetFindResponse> getMyPets() {
        List<Pet> petList = petFinder.findList(authorizationUtil.getId());

        return petList.stream()
                .map(PetFindResponse::of)
                .toList();
    }

    @PostMapping()
    public PetRegisterResponse register(@RequestBody @Valid PetRegisterRequest petRegisterRequest) {
        Pet pet = petManager.register(petRegisterRequest, authorizationUtil.getId());
        return PetRegisterResponse.of(pet);
    }

    @PatchMapping("/{petId}")
    public PetInfoUpdateResponse update(@PathVariable Long petId, @RequestBody @Valid PetInfoUpdateRequest petInfoUpdateRequest) {
        Pet pet = petManager.update(petId, petInfoUpdateRequest, authorizationUtil.getId());
        return PetInfoUpdateResponse.of(pet);
    }
}
