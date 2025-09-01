package com.mungdori.sponge.domain.trainer;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record HistoryCreateRequest(

        @NonNull
        String title,
        @NonNull
        String startDt,
        @Nullable
        String endDt,
        @NonNull
        String description
) {

}
