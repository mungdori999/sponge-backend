package com.mungdori.sponge.application.owner;

import com.mungdori.sponge.application.owner.provided.OwnerFinder;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerQueryService implements OwnerFinder {

    private final OwnerRepository ownerRepository;

    @Override
    public Owner findById(Long ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다." + ownerId));

    }

    @Override
    public Owner findByEmail(String email) {
        return ownerRepository.findByEmail(new Email(email)).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다." + email));

    }
}
