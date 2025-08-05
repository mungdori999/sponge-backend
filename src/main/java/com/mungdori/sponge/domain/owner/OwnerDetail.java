package com.mungdori.sponge.domain.owner;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OwnerDetail {

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;


    static OwnerDetail create() {
        OwnerDetail userDetail = new OwnerDetail();
        userDetail.registeredAt = LocalDateTime.now();
        return userDetail;
    }

    void activate() {
        activatedAt = LocalDateTime.now();
    }

    void deactivate() {
        deactivatedAt = LocalDateTime.now();
    }
}
