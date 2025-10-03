package com.mungdori.sponge.application.token;

import com.mungdori.sponge.application.token.provided.JWTManager;
import com.mungdori.sponge.application.token.required.RefreshTokenRepository;
import com.mungdori.sponge.domain.token.RefreshToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class JWTModifyService implements JWTManager {

    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public RefreshToken save(String refreshToken) {
        RefreshToken token = RefreshToken.create(refreshToken);

        refreshTokenRepository.save(token);

        return token;
    }

    @Override
    public void delete(String refreshToken) {
        RefreshToken foundToken = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow();

        refreshTokenRepository.delete(foundToken);
    }
}
