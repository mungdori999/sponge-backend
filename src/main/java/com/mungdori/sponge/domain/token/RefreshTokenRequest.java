package com.mungdori.sponge.domain.token;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest (

        @NotBlank
        String refreshToken) {


}
