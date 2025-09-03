package com.mungdori.sponge.application.trainer;

import com.mungdori.sponge.application.trainer.provided.TrainerManager;
import com.mungdori.sponge.application.trainer.required.TrainerRepository;
import com.mungdori.sponge.domain.owner.PasswordEncoder;
import com.mungdori.sponge.domain.trainer.Trainer;
import com.mungdori.sponge.domain.trainer.TrainerRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class TrainerModifyService implements TrainerManager {

    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Trainer register(TrainerRegisterRequest registerRequest) {
        Trainer trainer = Trainer.register(registerRequest, passwordEncoder);

        trainerRepository.save(trainer);


        return null;
    }
}
