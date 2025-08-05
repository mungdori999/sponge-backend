package com.mungdori.sponge.domain.owner;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OwnerDetail {
    private Long id;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;


     static OwnerDetail create() {
        OwnerDetail userDetail = new OwnerDetail();
        userDetail.registeredAt = LocalDateTime.now();
        return userDetail;
    }
}
