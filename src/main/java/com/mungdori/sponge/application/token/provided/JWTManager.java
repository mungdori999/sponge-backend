package com.mungdori.sponge.application.token.provided;

import com.mungdori.sponge.domain.token.RefreshToken;

public interface JWTManager {


    RefreshToken save (String email, String refreshToken);

    void delete(String refresh);
}
