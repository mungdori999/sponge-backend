package com.mungdori.sponge.adapter.webapi.dto;

import com.mungdori.sponge.domain.owner.Owner;

public record OwnerRegisterResponse(Long ownerId, String email, String nickname, String gender, String phoneNumber) {

    public static OwnerRegisterResponse of(Owner owner) {
        return new OwnerRegisterResponse(owner.getId(), owner.getEmail().address(), owner.getNickname(), owner.getGender().name(), owner.getPhoneNumber());
    }

}
