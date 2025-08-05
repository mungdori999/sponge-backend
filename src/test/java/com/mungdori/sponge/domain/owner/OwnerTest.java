package com.mungdori.sponge.domain.owner;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.shared.UserStatus.*;
import static com.mungdori.sponge.domain.owner.OwnerFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OwnerTest {

    Owner owner;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();
        owner = Owner.register(createOwnerRegisterRequest(), passwordEncoder);

    }

    @Test
    void registerOwner() {
        assertThat(owner.getStatus()).isEqualTo(PENDING);
        assertThat(owner.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void activate() {
        owner.activate();

        assertThat(owner.getStatus()).isEqualTo(ACTIVE);
    }
    
    @Test
    void activateFail() {
        owner.activate();

         Assertions.assertThatThrownBy(()-> owner.activate())
             .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        owner.activate();

        owner.deactivate();

        assertThat(owner.getStatus()).isEqualTo(DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(owner::deactivate).isInstanceOf(IllegalStateException.class);

        owner.activate();
        owner.deactivate();

        assertThatThrownBy(owner::deactivate).isInstanceOf(IllegalStateException.class);
    }

}