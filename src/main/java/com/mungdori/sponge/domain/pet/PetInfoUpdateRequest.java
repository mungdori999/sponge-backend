package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.shared.GenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record PetInfoUpdateRequest(
        @Size(min = 2, max = 10)
        String name,
        @NonNull
        String breed,
        @NonNull
        GenderType gender,
        @Min(1)
        int age,
        @Positive
        float weight
) {
}
