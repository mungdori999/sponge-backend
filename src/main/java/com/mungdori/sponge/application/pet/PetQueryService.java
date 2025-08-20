package com.mungdori.sponge.application.pet;

import com.mungdori.sponge.application.pet.provided.PetFinder;
import com.mungdori.sponge.application.pet.required.PetRepository;
import com.mungdori.sponge.domain.pet.Pet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PetQueryService implements PetFinder {

    private final PetRepository petRepository;

    @Override
    public Pet find(Long petId) {
        return petRepository.findById(petId).orElseThrow(
                () -> new IllegalArgumentException("반려동물을 찾을 수 없습니다." + petId));
    }
}
