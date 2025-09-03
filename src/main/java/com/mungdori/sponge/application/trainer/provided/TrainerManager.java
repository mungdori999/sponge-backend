package com.mungdori.sponge.application.trainer.provided;

import com.mungdori.sponge.domain.trainer.Trainer;
import com.mungdori.sponge.domain.trainer.TrainerRegisterRequest;
import jakarta.validation.Valid;

public interface TrainerManager {

    Trainer register(@Valid TrainerRegisterRequest registerRequest);
}
