package com.mungdori.sponge.adapter.webapi.dto;

import com.mungdori.sponge.domain.owner.Owner;

public record OwnerInfoUpdateResponse(Long ownerId, String email, String nickname, String gender, String phoneNumber) {

    public static OwnerInfoUpdateResponse of(Owner owner) {
        return new OwnerInfoUpdateResponse(owner.getId(), owner.getEmail().address(), owner.getNickname(), owner.getGender().name(), owner.getPhoneNumber());
    }

}
