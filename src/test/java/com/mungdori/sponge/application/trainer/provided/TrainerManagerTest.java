package com.mungdori.sponge.application.trainer.provided;

import com.mungdori.sponge.application.trainer.TrainerModifyService;
import com.mungdori.sponge.domain.shared.UserStatus;
import com.mungdori.sponge.domain.trainer.Trainer;
import com.mungdori.sponge.domain.trainer.TrainerFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
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


    Trainer createTrainer() {
        Trainer trainer = Trainer.register(TrainerFixture.createTrainerRegisterRequest(), createPasswordEncoder());

        entityManager.persist(trainer);

        entityManager.flush();
        entityManager.clear();

        return trainer;
    }
}