package com.mungdori.sponge.application.owner;

import com.mungdori.sponge.application.owner.provided.OwnerRegister;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.domain.owner.DuplicateNicknameException;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import com.mungdori.sponge.domain.owner.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class OwnerModifyService implements OwnerRegister {

    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepository;

    @Override
    public Owner register(OwnerRegisterRequest registerRequest) {
        Owner owner = Owner.register(registerRequest, passwordEncoder);

        checkDuplicateNickname(owner.getNickname());

        ownerRepository.save(owner);

        return owner;
    }

    private void checkDuplicateNickname(String nickname) {
        if (ownerRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateNicknameException("nickname이 이미 사용중입니다. " + nickname);
        }
    }
}
