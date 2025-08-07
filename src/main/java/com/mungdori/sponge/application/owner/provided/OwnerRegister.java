package com.mungdori.sponge.application.owner.provided;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import jakarta.validation.Valid;

public interface OwnerRegister {

    Owner register(@Valid OwnerRegisterRequest registerRequest);
}
