package com.mungdori.sponge.domain.token;

import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.shared.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends AbstractEntity {

    @Column(name = "email", nullable = false)
    @Embedded
    private Email email;

    @Column(name = "refresh", nullable = false, length = 512)
    private String refresh;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    public static RefreshToken create(Email email, String refresh) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.email = email;
        refreshToken.refresh = refresh;
        refreshToken.createdDate = LocalDateTime.now();

        return refreshToken;
    }
}
