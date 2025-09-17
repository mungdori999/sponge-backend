package com.mungdori.sponge.adapter.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungdori.sponge.adapter.jwt.JWTUtil;
import com.mungdori.sponge.adapter.security.UserDetailsImpl;
import com.mungdori.sponge.application.token.provided.JWTManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("LoginSuccessHandler")
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTManager jwtManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // email, loginType
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        String nickname = userDetails.getUsername();
        String loginType = userDetails.getAuthorities().iterator().next().getAuthority();


        // JWT(Access/Refresh) 발급
        String accessToken = JWTUtil.createJWT(email, nickname, loginType, true);
        String refreshToken = JWTUtil.createJWT(email, nickname, loginType, false);

        // Refresh Token 저장 (화이트리스트)
        jwtManager.save(email, refreshToken);

        // 응답 JSON 데이터 구성
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("email", email);
        tokens.put("nickname", nickname);

        // 응답 세팅
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(tokens));
        response.getWriter().flush();
    }
}
