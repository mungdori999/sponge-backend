package com.mungdori.sponge.application.owner.provided;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerInfoUpdateRequest;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import jakarta.validation.Valid;

public interface OwnerManager {

    Owner register(@Valid OwnerRegisterRequest registerRequest);

    Owner update(Long ownerId,@Valid OwnerInfoUpdateRequest updateRequest);
}
