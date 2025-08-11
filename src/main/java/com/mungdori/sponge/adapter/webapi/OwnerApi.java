package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.webapi.dto.OwnerRegisterResponse;
import com.mungdori.sponge.application.owner.provided.OwnerRegister;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerApi {

    private final OwnerRegister ownerRegister;


    @PostMapping("/api/owner")
    public OwnerRegisterResponse register(@RequestBody @Valid OwnerRegisterRequest registerRequest) {
        Owner owner = ownerRegister.register(registerRequest);
        return OwnerRegisterResponse.of(owner);
    }
}
