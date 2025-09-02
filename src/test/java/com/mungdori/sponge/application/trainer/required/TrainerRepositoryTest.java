package com.mungdori.sponge.application.trainer.required;

import com.mungdori.sponge.domain.trainer.Trainer;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.mungdori.sponge.domain.trainer.TrainerFixture.createPasswordEncoder;
import static com.mungdori.sponge.domain.trainer.TrainerFixture.createTrainerRegisterRequest;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
record TrainerRepositoryTest(TrainerRepository trainerRepository, EntityManager entityManager) {

    @Test
    void createTrainer() {
        Trainer trainer = Trainer.register(createTrainerRegisterRequest(), createPasswordEncoder());

        assertThat(trainer.getId()).isNull();

        trainerRepository.save(trainer);

        assertThat(trainer.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var found = trainerRepository.findById(trainer.getId()).orElseThrow();

        assertThat(trainer.getId()).isEqualTo(found.getId());
        assertThat(trainer.getNickname()).isEqualTo(found.getNickname());
    }

    @Test
    void duplicateEmail() {
        Trainer trainer = Trainer.register(createTrainerRegisterRequest(), createPasswordEncoder());

        trainerRepository.save(trainer);

        Trainer trainer2 = Trainer.register(createTrainerRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> trainerRepository.save(trainer2))
                .isInstanceOf(DataIntegrityViolationException.class);


    }

}