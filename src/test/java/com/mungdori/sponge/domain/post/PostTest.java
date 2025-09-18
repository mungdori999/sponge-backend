package com.mungdori.sponge.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.post.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    Post post;

    @BeforeEach
    void setUp() {
        post = Post.create(createPostRequest());
    }

    @Test
    void create() {
        assertThat(post.getTitle()).isNotNull();
        assertThat(post.getCreatedAt()).isNotNull();
    }
}