package com.mungdori.sponge.domain.token;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 2592000)
public class RefreshToken {

    @Id
    private String refreshToken;
    private Long id;

    public static RefreshToken create(String token) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.refreshToken = token;
        refreshToken.id = UUID.randomUUID().getMostSignificantBits();

        return refreshToken;
    }
}
