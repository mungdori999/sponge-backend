package com.mungdori.sponge.domain.shared;

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
public class Detail extends AbstractEntity {

    private LocalDateTime registeredAt;

    private LocalDateTime deactivatedAt;


    public static Detail create() {
        Detail detail = new Detail();
        detail.registeredAt = LocalDateTime.now();
        return detail;
    }

    public void deactivate() {
        deactivatedAt = LocalDateTime.now();
    }
}
