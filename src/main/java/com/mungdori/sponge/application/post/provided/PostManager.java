package com.mungdori.sponge.application.post.provided;

import com.mungdori.sponge.domain.post.Post;
import com.mungdori.sponge.domain.post.PostCreateRequest;
import com.mungdori.sponge.domain.post.PostInfoUpdateRequest;
import jakarta.validation.Valid;

public interface PostManager {

    Post create(@Valid PostCreateRequest postCreateRequest, Long petId, String email);

    Post update(Long postId, @Valid PostInfoUpdateRequest postInfoUpdateRequest, Long petId, String email);
}
