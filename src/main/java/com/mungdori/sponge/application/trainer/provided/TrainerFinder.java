package com.mungdori.sponge.application.trainer.provided;

import com.mungdori.sponge.domain.trainer.Trainer;

public interface TrainerFinder {

    Trainer find(Long trainerId);


}
