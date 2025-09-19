package com.mungdori.sponge.domain.post;

import org.springframework.lang.NonNull;

import java.util.List;

public record PostInfoUpdateRequest(

        @NonNull
        String title,

        @NonNull
        String content,

        @NonNull
        String duration,

        List<Long> categoryCodeList,

        List<String> hashTagList,

        List<String> fileUrlList
) {
}
