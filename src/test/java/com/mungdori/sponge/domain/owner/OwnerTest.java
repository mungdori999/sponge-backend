package com.mungdori.sponge.domain.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.shared.UserStatus.ACTIVE;
import static com.mungdori.sponge.domain.shared.UserStatus.DEACTIVATED;
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
        assertThat(owner.getStatus()).isEqualTo(ACTIVE);
        assertThat(owner.getDetail().getRegisteredAt()).isNotNull();
    }


    @Test
    void deactivate() {
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
        var request = new OwnerInfoUpdateRequest("testName");
        owner.updateInfo(request);

        assertThat(owner.getNickname()).isEqualTo(request.nickname());
    }

    @Test
    void updateInfoFail() {
        owner.deactivate();

        assertThatThrownBy(() -> owner.updateInfo(new OwnerInfoUpdateRequest("testName")))
                .isInstanceOf(IllegalStateException.class);
    }
}