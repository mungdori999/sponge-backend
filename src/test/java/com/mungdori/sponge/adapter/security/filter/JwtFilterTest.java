package com.mungdori.sponge.adapter.security.filter;


import com.mungdori.sponge.adapter.security.utils.JWTUtil;
import com.mungdori.sponge.adapter.security.utils.LoginType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
record JwtFilterTest(JwtFilter jwtFilter) {

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext(); // 테스트마다 SecurityContext 초기화
    }

    @Test
    void notToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        jwtFilter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(auth).isNull();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void token() throws Exception {
        String token = JWTUtil.createJWT(1L, "nickname", LoginType.OWNER.name(), true);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        jwtFilter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(auth).isNotNull();
        assertThat(Long.valueOf(auth.getName())).isEqualTo(1L);
        assertThat(auth.getAuthorities())
                .extracting("authority")
                .containsExactly("USER");
    }

    @Test
    void invalidToken() throws Exception {
        String token = "invalid.token.value";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        jwtFilter.doFilterInternal(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

}
