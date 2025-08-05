package com.mungdori.sponge.domain.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {


    @Test
    void equality() {
        var email1 = new Email("mungdori999@gmail.com");
        var email2 = new Email("mungdori999@gmail.com");

        assertThat(email1).isEqualTo(email2);
    }

}