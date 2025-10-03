package com.mungdori.sponge.domain.owner;

import com.mungdori.sponge.domain.shared.GenderType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

public record OwnerInfoUpdateRequest(
        @Size(min = 2, max = 10)
        String nickname,

        @NotNull
        GenderType gender,

        @Nullable
        String phoneNumber
) {
}
