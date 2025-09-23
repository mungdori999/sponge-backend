package com.mungdori.sponge.application.post.provided;

import com.mungdori.sponge.domain.post.Post;
import com.mungdori.sponge.domain.post.PostCreateRequest;
import jakarta.validation.Valid;

public interface PostManager {

    Post create(@Valid PostCreateRequest postCreateRequest,Long petId,String email);
}
