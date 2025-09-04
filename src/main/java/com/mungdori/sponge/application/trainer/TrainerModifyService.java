package com.mungdori.sponge.application.trainer;

import com.mungdori.sponge.application.trainer.provided.TrainerFinder;
import com.mungdori.sponge.application.trainer.provided.TrainerManager;
import com.mungdori.sponge.application.trainer.required.TrainerRepository;
import com.mungdori.sponge.domain.owner.DuplicateEmailException;
import com.mungdori.sponge.domain.owner.DuplicateNicknameException;
import com.mungdori.sponge.domain.owner.PasswordEncoder;
import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.trainer.Trainer;
import com.mungdori.sponge.domain.trainer.TrainerRegisterRequest;
import com.mungdori.sponge.domain.trainer.TrainerUpdateRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class TrainerModifyService implements TrainerManager {

    private final TrainerRepository trainerRepository;
    private final TrainerFinder trainerFinder;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Trainer register(TrainerRegisterRequest registerRequest) {
        Trainer trainer = Trainer.register(registerRequest, passwordEncoder);

        checkDuplicateNickname(trainer.getNickname());
        checkDuplicateEmail(trainer.getEmail());

        trainer = trainerRepository.save(trainer);

        return trainer;
    }

    @Override
    public Trainer update(Long trainerId, TrainerUpdateRequest updateRequest) {
        Trainer trainer = trainerFinder.find(trainerId);

        checkDuplicateNickname(updateRequest.nickname());

        trainer.updateInfo(updateRequest);

        trainer = trainerRepository.save(trainer);

        return trainer;
    }

    private void checkDuplicateEmail(Email email) {
        if (trainerRepository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException("email이 이미 사용중입니다. " + email.address());
        }
    }

    private void checkDuplicateNickname(String nickname) {
        if (trainerRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateNicknameException("nickname이 이미 사용중입니다. " + nickname);
        }
    }
}
