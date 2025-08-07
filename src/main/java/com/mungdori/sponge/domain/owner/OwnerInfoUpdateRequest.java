package com.mungdori.sponge.domain.owner;

import jakarta.validation.constraints.Size;

public record OwnerInfoUpdateRequest(
        @Size(min = 2, max = 10)
        String nickname
) {
}
