package com.mungdori.sponge.application.token.required;

import com.mungdori.sponge.domain.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken save (RefreshToken refreshToken);
}
