package com.mungdori.sponge.domain.trainer;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record HistoryCreateRequest(

        @NotBlank
        String title,
        @NotBlank
        String startDt,
        @Nullable
        String endDt,
        @NotBlank
        String description
) {

}
