package com.mungdori.sponge.domain.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.shared.UserStatus.*;
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
        assertThat(owner.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void activateFail() {
        owner.activate();

        assertThatThrownBy(() -> owner.activate())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        owner.activate();

        owner.deactivate();

        assertThat(owner.getStatus()).isEqualTo(DEACTIVATED);
        assertThat(owner.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void verifyPassword() {
        assertThat(owner.verifyPassword("longsecret", passwordEncoder)).isTrue();
        assertThat(owner.verifyPassword("longhello", passwordEncoder)).isFalse();
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(owner::deactivate).isInstanceOf(IllegalStateException.class);

        owner.activate();
        owner.deactivate();

        assertThatThrownBy(owner::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Owner.register(createOwnerRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Owner.register(createOwnerRegisterRequest(), passwordEncoder);

    }

    @Test
    void updateInfo() {
        owner.activate();

        var request = new OwnerInfoUpdateRequest("testName");
        owner.updateInfo(request);

        assertThat(owner.getNickName()).isEqualTo(request.name());
    }

    @Test
    void updateInfoFail() {
        assertThatThrownBy(() -> owner.updateInfo(new OwnerInfoUpdateRequest("김지용1")))
                .isInstanceOf(IllegalStateException.class);
    }
}