package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.security.utils.AuthorizationUtil;
import com.mungdori.sponge.adapter.webapi.dto.owner.OwnerFindResponse;
import com.mungdori.sponge.adapter.webapi.dto.owner.OwnerInfoUpdateResponse;
import com.mungdori.sponge.adapter.webapi.dto.owner.OwnerRegisterResponse;
import com.mungdori.sponge.application.owner.provided.OwnerFinder;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerInfoUpdateRequest;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owner")
public class OwnerApi {

    private final OwnerManager ownerManager;
    private final OwnerFinder ownerFinder;
    private final AuthorizationUtil authorizationUtil;

    @GetMapping("/{ownerId}")
    public OwnerFindResponse getOwner(@PathVariable Long ownerId) {
        Owner owner = ownerFinder.findById(ownerId);
        return OwnerFindResponse.of(owner);
    }

    @GetMapping()
    public OwnerFindResponse getMyInfo(){
        Owner owner = ownerFinder.findByEmail(authorizationUtil.getEmail());
        return OwnerFindResponse.of(owner);
    }


    @PostMapping()
    public OwnerRegisterResponse register(@RequestBody @Valid OwnerRegisterRequest registerRequest) {
        Owner owner = ownerManager.register(registerRequest);
        return OwnerRegisterResponse.of(owner);
    }

    @PatchMapping("/{ownerId}")
    public OwnerInfoUpdateResponse update(@PathVariable Long ownerId, @RequestBody @Valid OwnerInfoUpdateRequest updateRequest) {
        Owner owner = ownerManager.update(ownerId, updateRequest);
        return OwnerInfoUpdateResponse.of(owner);
    }
}
