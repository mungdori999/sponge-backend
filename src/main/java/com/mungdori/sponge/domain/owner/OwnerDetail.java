package com.mungdori.sponge.domain.owner;

import com.mungdori.sponge.domain.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OwnerDetail extends AbstractEntity {

    private LocalDateTime registeredAt;


    private LocalDateTime deactivatedAt;


    static OwnerDetail create() {
        OwnerDetail userDetail = new OwnerDetail();
        userDetail.registeredAt = LocalDateTime.now();
        return userDetail;
    }

    void deactivate() {
        deactivatedAt = LocalDateTime.now();
    }
}
