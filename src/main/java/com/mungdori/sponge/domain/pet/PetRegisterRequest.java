package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.shared.GenderType;
import jakarta.validation.constraints.*;
import org.springframework.lang.Nullable;

public record PetRegisterRequest(
        @Size(min = 2, max = 10)
        String name,
        @NotBlank
        String breed,
        @NotNull
        GenderType gender,
        @Min(1)
        int age,
        @Positive
        float weight,
        @Nullable
        String petImgUrl
) {
}
