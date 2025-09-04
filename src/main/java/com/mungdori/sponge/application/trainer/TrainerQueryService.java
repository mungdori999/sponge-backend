package com.mungdori.sponge.application.trainer;

import com.mungdori.sponge.application.trainer.provided.TrainerFinder;
import com.mungdori.sponge.application.trainer.required.TrainerRepository;
import com.mungdori.sponge.domain.trainer.Trainer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class TrainerQueryService implements TrainerFinder {

    private final TrainerRepository trainerRepository;

    @Override
    public Trainer find(Long trainerId) {
        return trainerRepository.findById(trainerId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다." + trainerId));
    }
}
