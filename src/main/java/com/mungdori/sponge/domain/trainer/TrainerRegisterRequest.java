package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.shared.GenderType;
import jakarta.validation.constraints.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public record TrainerRegisterRequest(@Email
                                     String email,
                                     @Size(min = 2, max = 10)
                                     String nickname,
                                     @NotNull
                                     GenderType gender,
                                     @Nullable
                                     String phoneNumber,
                                     @Size(min = 8, max = 100)
                                     String password,
                                     @Nullable
                                     String introduction,
                                     @Min(0)
                                     int employmentYear,

                                     List<HistoryCreateRequest> historyCreateRequestList
                                     ) {
}
