package com.mungdori.sponge.adapter.webapi.dto;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.GenderType;

public record OwnerRegisterResponse(Long ownerId, String email, String nickname, GenderType gender, String phoneNumber) {

    public static OwnerRegisterResponse of(Owner owner) {
        return new OwnerRegisterResponse(owner.getId(), owner.getEmail().address(), owner.getNickname(), owner.getGender(), owner.getPhoneNumber());
    }

}
