package com.mungdori.sponge.application.owner;

import com.mungdori.sponge.application.owner.provided.OwnerFinder;
import com.mungdori.sponge.application.owner.provided.OwnerRegister;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.domain.owner.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class OwnerModifyService implements OwnerRegister {

    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;
    private final OwnerFinder ownerFinder;

    @Override
    public Owner register(OwnerRegisterRequest registerRequest) {
        Owner owner = Owner.register(registerRequest, passwordEncoder);

        checkDuplicateNickname(owner.getNickname());

        owner = ownerRepository.save(owner);

        return owner;
    }

    @Override
    public Owner update(Long ownerId, OwnerInfoUpdateRequest updateRequest) {
        Owner owner = ownerFinder.find(ownerId);

        checkDuplicateNickname(updateRequest.nickname());

        owner.updateInfo(updateRequest);

        ownerRepository.save(owner);

        return owner;
    }

    private void checkDuplicateNickname(String nickname) {
        if (ownerRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateNicknameException("nickname이 이미 사용중입니다. " + nickname);
        }
    }
}
