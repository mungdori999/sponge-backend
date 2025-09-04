package com.mungdori.sponge.application.trainer.provided;

import com.mungdori.sponge.application.trainer.TrainerModifyService;
import com.mungdori.sponge.domain.owner.DuplicateEmailException;
import com.mungdori.sponge.domain.owner.DuplicateNicknameException;
import com.mungdori.sponge.domain.shared.UserStatus;
import com.mungdori.sponge.domain.trainer.Trainer;
import com.mungdori.sponge.domain.trainer.TrainerFixture;
import com.mungdori.sponge.domain.trainer.TrainerUpdateRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
record TrainerManagerTest(TrainerModifyService modifyService, EntityManager entityManager) {


    @Test
    void register() {
        Trainer trainer = createTrainer();

        assertThat(trainer.getId()).isNotNull();
        assertThat(trainer.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void duplicateNickname() {
        createTrainer();

        assertThatThrownBy(() -> modifyService.register(TrainerFixture.createTrainerRegisterRequest("new@naver.com", "nickname")))
                .isInstanceOf(DuplicateNicknameException.class);
    }

    @Test
    void duplicateEmail() {
        createTrainer();

        assertThatThrownBy(() -> modifyService.register(TrainerFixture.createTrainerRegisterRequest("mungdori999@gmail.com", "newname")))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void updateInfo() {
        Trainer trainer = createTrainer();

        var request = TrainerFixture.createTrainerUpdateRequest();

        trainer = modifyService.update(trainer.getId(), request);

        assertThat(trainer.getNickname()).isEqualTo(request.nickname());
        assertThat(trainer.getIntroduction()).isEqualTo(request.introduction());
    }


    Trainer createTrainer() {
        Trainer trainer = Trainer.register(TrainerFixture.createTrainerRegisterRequest(), createPasswordEncoder());

        entityManager.persist(trainer);

        entityManager.flush();
        entityManager.clear();

        return trainer;
    }
}