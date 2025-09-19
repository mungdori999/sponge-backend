package com.mungdori.sponge.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.post.PostFixture.createPostCreateRequest;
import static org.assertj.core.api.Assertions.assertThat;

class PostTagTest {

    Post post;

    @BeforeEach
    void setUp() {
        post = Post.create(createPostCreateRequest(), 1L, 1L);
    }


    @Test
    void create() {
        PostTag postTag = PostTag.create("졸음", post);

        assertThat(postTag.getHasTag()).isEqualTo("졸음");
        assertThat(postTag.getPost()).isEqualTo(post);

    }

}