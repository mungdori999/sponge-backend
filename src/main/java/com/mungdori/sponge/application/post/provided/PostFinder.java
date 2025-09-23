package com.mungdori.sponge.application.post.provided;

import com.mungdori.sponge.domain.post.Post;

public interface PostFinder {

    Post find(Long postId);
}
