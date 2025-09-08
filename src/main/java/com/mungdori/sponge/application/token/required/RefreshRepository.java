package com.mungdori.sponge.application.token.required;

import com.mungdori.sponge.domain.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken save (RefreshToken refreshToken);

    Optional<RefreshToken> findByRefresh(String refresh);

    void deleteByRefresh (String refresh);
}
