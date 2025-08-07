package com.mungdori.sponge.application.owner.provided;

import com.mungdori.sponge.domain.owner.Owner;

public interface OwnerFinder {

    Owner find(Long ownerId);
}
