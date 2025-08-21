package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.webapi.dto.OwnerInfoUpdateResponse;
import com.mungdori.sponge.adapter.webapi.dto.OwnerRegisterResponse;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerInfoUpdateRequest;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OwnerApi {

    private final OwnerManager ownerManager;


    @PostMapping("/api/owner")
    public OwnerRegisterResponse register(@RequestBody @Valid OwnerRegisterRequest registerRequest) {
        Owner owner = ownerManager.register(registerRequest);
        return OwnerRegisterResponse.of(owner);
    }

    @PatchMapping("/api/owner/{ownerId}")
    public OwnerInfoUpdateResponse update(@PathVariable Long ownerId, @RequestBody @Valid OwnerInfoUpdateRequest updateRequest) {
        Owner owner = ownerManager.update(ownerId, updateRequest);
        return OwnerInfoUpdateResponse.of(owner);
    }
}
