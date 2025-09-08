package com.mungdori.sponge.application.token;

import com.mungdori.sponge.application.token.provided.JWTManager;
import com.mungdori.sponge.application.token.required.RefreshRepository;
import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.token.RefreshToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class JWTModifyService implements JWTManager {

    private final RefreshRepository refreshRepository;


    @Override
    public RefreshToken save(String email, String refreshToken) {
        RefreshToken token = RefreshToken.create(new Email(email), refreshToken);

        refreshRepository.save(token);

        return token;
    }

    @Override
    public void delete(String refresh) {
        refreshRepository.deleteByRefresh(refresh);
    }
}
