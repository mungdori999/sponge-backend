package com.mungdori.sponge.adapter.webapi.dto;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.GenderType;

public record OwnerInfoUpdateResponse(Long ownerId, String email, String nickname, GenderType gender, String phoneNumber) {

    public static OwnerInfoUpdateResponse of(Owner owner) {
        return new OwnerInfoUpdateResponse(owner.getId(), owner.getEmail().address(), owner.getNickname(), owner.getGender(), owner.getPhoneNumber());
    }

}
