package com.mungdori.sponge.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.post.PostFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    Post post;

    @BeforeEach
    void setUp() {
        post = Post.create(createPostCreateRequest(), 1L, 1L);
    }

    @Test
    void create() {
        assertThat(post.getTitle()).isNotNull();
        assertThat(post.getCreatedAt()).isNotNull();
    }

    @Test
    void update() {
        var request = createPostInfoRequest();
        Post update = post.update(request);

        assertThat(update.getTitle()).isEqualTo(request.title());
        assertThat(update.getUpdatedAt()).isNotNull();
    }


}