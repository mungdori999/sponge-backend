package com.mungdori.sponge.adapter.webapi.dto.post;

import com.mungdori.sponge.domain.post.Post;
import com.mungdori.sponge.domain.post.PostCategory;
import com.mungdori.sponge.domain.post.PostFile;
import com.mungdori.sponge.domain.post.PostTag;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public record PostCreateResponse(

        Long postId,
        String title,
        String content,
        String duration,
        LocalDateTime createdAt,
        List<Long> categoryCodeList,
        List<String> hashTagList,
        List<String> fileUrlList
) {
    public static PostCreateResponse of(@NonNull Post post) {
        return new PostCreateResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDuration(),
                post.getCreatedAt(),

                // categoryCodeList
                post.getCategoryList().stream()
                        .map(PostCategory::getCategoryCode)
                        .toList(),

                // hashTagList
                post.getTagList().stream()
                        .map(PostTag::getHasTag)
                        .toList(),

                // fileUrlList
                post.getFileList().stream()
                        .map(PostFile::getFileUrl)
                        .toList()
        );
    }
}
