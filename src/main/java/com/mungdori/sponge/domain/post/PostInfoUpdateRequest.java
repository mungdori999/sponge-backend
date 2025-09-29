package com.mungdori.sponge.domain.post;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.util.List;

public record PostInfoUpdateRequest(

        @NotBlank
        String title,

        @NotBlank
        String content,

        @NotBlank
        String duration,

        List<Long> categoryCodeList,

        List<String> hashTagList,

        List<String> fileUrlList
) {
}
