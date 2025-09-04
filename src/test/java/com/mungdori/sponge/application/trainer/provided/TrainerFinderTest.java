package com.mungdori.sponge.application.trainer.provided;

import com.mungdori.sponge.domain.trainer.Trainer;
import com.mungdori.sponge.domain.trainer.TrainerFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
record TrainerFinderTest(TrainerFinder trainerFinder, TrainerManager trainerManager, EntityManager entityManager) {


    @Test
    void trainerFind() {
        Trainer trainer = trainerManager.register(TrainerFixture.createTrainerRegisterRequest());

        entityManager.flush();
        entityManager.clear();

        Trainer found = trainerFinder.find(trainer.getId());

        assertThat(found.getId()).isEqualTo(trainer.getId());
    }

    @Test
    void trainerFindFail() {
        trainerManager.register(TrainerFixture.createTrainerRegisterRequest());

        entityManager.flush();
        entityManager.clear();

        Assertions.assertThatThrownBy(() -> trainerFinder.find(999L))
                .isInstanceOf(IllegalArgumentException.class);
    }

}