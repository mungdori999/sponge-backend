package com.mungdori.sponge.adapter.webapi.dto.owner;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.GenderType;

public record OwnerFindResponse(Long ownerId, String nickname) {

    public static OwnerFindResponse of(Owner owner) {
        return  new OwnerFindResponse(owner.getId(),owner.getNickname());
    }
}
