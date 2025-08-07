package com.mungdori.sponge.application.owner.required;

import com.mungdori.sponge.domain.owner.Owner;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface OwnerRepository extends Repository<Owner, Long> {

    Owner save(Owner owner);

    Optional<Owner> findByNickname(String nickName);
}
