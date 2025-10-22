package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.security.utils.JWTUtil;
import com.mungdori.sponge.adapter.webapi.dto.token.ReissueResponse;
import com.mungdori.sponge.application.token.provided.JWTManager;
import com.mungdori.sponge.domain.token.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reissue")
public class ReissueApi {

    private final JWTManager jwtManager;

    @PostMapping
    private ReissueResponse reissue(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();

        Long id = JWTUtil.getId(refreshToken);
        String nickname = JWTUtil.getNickname(refreshToken);
        String loginType = JWTUtil.getLoginType(refreshToken);

        jwtManager.delete(refreshToken);

        String newAccessToken = JWTUtil.createJWT(id, nickname, loginType, true);
        String newRefreshToken = JWTUtil.createJWT(id, nickname, loginType, false);

        jwtManager.save(newRefreshToken);

        return ReissueResponse.of(newAccessToken, newRefreshToken);

    }
}
