package com.mungdori.sponge.application.trainer.required;

import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.trainer.Trainer;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface TrainerRepository extends Repository<Trainer, Long> {

    Trainer save(Trainer trainer);

    Optional<Trainer> findByEmail(Email email);

    Optional<Trainer> findById(Long trainerId);

    Optional<Trainer> findByNickname(String nickname);
}
