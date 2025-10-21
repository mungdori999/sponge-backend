package com.mungdori.sponge.adapter.security.filter;

import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import com.mungdori.sponge.adapter.security.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);

        if (token == null || request.getRequestURI().startsWith("/reissue")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!JWTUtil.isValid(token, true)) {
            writeUnauthorizedResponse(response);
            return;
        }

        Authentication authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     */
    private String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7); // "Bearer " 이후 문자열
    }

    /**
     * JWT에서 Authentication 객체 생성
     */
    private Authentication getAuthentication(String token) {
        Long id = JWTUtil.getId(token);
        String loginType = JWTUtil.getLoginType(token);

        return new LoginTypeAuthenticationToken(
                id, null, List.of(new SimpleGrantedAuthority("USER")), loginType);
    }

    /**
     * 401 Unauthorized 응답 작성
     */
    private void writeUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"토큰 만료 또는 유효하지 않은 토큰\"}");
    }
}
