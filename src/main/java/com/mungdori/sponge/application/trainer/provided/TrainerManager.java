package com.mungdori.sponge.application.trainer.provided;

import com.mungdori.sponge.domain.trainer.Trainer;
import com.mungdori.sponge.domain.trainer.TrainerRegisterRequest;
import com.mungdori.sponge.domain.trainer.TrainerUpdateRequest;
import jakarta.validation.Valid;

public interface TrainerManager {

    Trainer register(@Valid TrainerRegisterRequest registerRequest);

    Trainer update(Long trainerId, @Valid TrainerUpdateRequest request);
}
