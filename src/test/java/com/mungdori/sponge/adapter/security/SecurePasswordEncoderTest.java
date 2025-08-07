package com.mungdori.sponge.adapter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

record SecurePasswordEncoderTest() {


    @Test
    void securePasswordEncoder() {
        SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();

        String passwordHash = securePasswordEncoder.encode("longsecret");

        assertThat(securePasswordEncoder.matches("longsecret", passwordHash)).isTrue();
        assertThat(securePasswordEncoder.matches("longwrong", passwordHash)).isFalse();

    }

}