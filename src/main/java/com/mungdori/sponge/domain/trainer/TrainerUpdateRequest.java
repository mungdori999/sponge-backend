package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.shared.GenderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public record TrainerUpdateRequest(@Size(min = 2, max = 10)
                                   String nickname,
                                   @NonNull
                                   GenderType gender,
                                   @Nullable
                                   String phoneNumber,
                                   @Nullable
                                   String introduction,
                                   @Min(0)
                                   int employmentYear,

                                   List<HistoryCreateRequest> historyCreateRequestList) {
}
