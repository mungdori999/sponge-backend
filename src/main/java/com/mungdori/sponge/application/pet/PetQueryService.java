package com.mungdori.sponge.application.pet;

import com.mungdori.sponge.application.owner.provided.OwnerFinder;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.application.pet.provided.PetFinder;
import com.mungdori.sponge.application.pet.required.PetRepository;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.shared.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PetQueryService implements PetFinder {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public Pet find(Long petId) {
        return petRepository.findById(petId).orElseThrow(
                () -> new IllegalArgumentException("반려동물을 찾을 수 없습니다." + petId));
    }

    @Override
    public List<Pet> findList(String email) {
        Owner owner = ownerRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 가 없습니다."));

        return petRepository.findByOwnerId(owner.getId());
    }
}
