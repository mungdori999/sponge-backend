package com.mungdori.sponge.application.token.required;

import com.mungdori.sponge.domain.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    RefreshToken save (RefreshToken refreshToken);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void delete(RefreshToken refreshToken);
}
