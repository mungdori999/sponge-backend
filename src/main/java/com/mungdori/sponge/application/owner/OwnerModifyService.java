package com.mungdori.sponge.application.owner;

import com.mungdori.sponge.application.owner.provided.OwnerFinder;
import com.mungdori.sponge.application.owner.provided.OwnerManager;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.domain.owner.*;
import com.mungdori.sponge.domain.shared.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class OwnerModifyService implements OwnerManager {

    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;
    private final OwnerFinder ownerFinder;

    @Override
    public Owner register(OwnerRegisterRequest registerRequest) {
        Owner owner = Owner.register(registerRequest, passwordEncoder);

        checkDuplicateNickname(owner.getNickname());
        checkDuplicateEmail(owner.getEmail());

        owner = ownerRepository.save(owner);

        return owner;
    }



    @Override
    public Owner update(Long ownerId, OwnerInfoUpdateRequest updateRequest) {
        Owner owner = ownerFinder.findById(ownerId);

        checkDuplicateNickname(updateRequest.nickname());

        owner.updateInfo(updateRequest);

        ownerRepository.save(owner);

        return owner;
    }

    private void checkDuplicateEmail(Email email) {
        if (ownerRepository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException("email이 이미 사용중입니다. " + email.address());
        }
    }

    private void checkDuplicateNickname(String nickname) {
        if (ownerRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateNicknameException("nickname이 이미 사용중입니다. " + nickname);
        }
    }
}
