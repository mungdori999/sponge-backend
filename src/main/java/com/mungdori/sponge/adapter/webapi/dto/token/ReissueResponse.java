package com.mungdori.sponge.adapter.webapi.dto.token;

public record ReissueResponse(
        String accessToken,
        String refreshToken
) {

    public static ReissueResponse of(String accessToken, String refreshToken) {
        return new ReissueResponse(accessToken, refreshToken);
    }

}
