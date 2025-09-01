package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.owner.PasswordEncoder;
import com.mungdori.sponge.domain.shared.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.shared.UserStatus.DEACTIVATED;
import static com.mungdori.sponge.domain.trainer.TrainerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.trainer.TrainerFixture.createTrainerRegisterRequest;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class TrainerTest {

    Trainer trainer;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();
        trainer = Trainer.register(createTrainerRegisterRequest(), createPasswordEncoder());
    }

    @Test
    void register() {
        assertThat(trainer.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(trainer.getDetail().getRegisteredAt()).isNotNull();
        assertThat(trainer.getHistoryList()).isNotEmpty();
    }

    @Test
    void deactivate() {
        trainer.deactivate();

        assertThat(trainer.getStatus()).isEqualTo(DEACTIVATED);
        assertThat(trainer.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void verifyPassword() {
        assertThat(trainer.verifyPassword("longsecret", passwordEncoder)).isTrue();
        assertThat(trainer.verifyPassword("wrong", passwordEncoder)).isFalse();

    }

    @Test
    void changePassword() {
        trainer.changePassword("changePassword", passwordEncoder);

        assertThat(trainer.verifyPassword("changePassword", passwordEncoder)).isTrue();
    }

}