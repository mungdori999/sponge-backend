package com.mungdori.sponge.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.post.PostFixture.createPostCreateRequest;
import static org.assertj.core.api.Assertions.assertThat;

class PostCategoryTest {

    Post post;

    @BeforeEach
    void setUp() {
        post = Post.create(createPostCreateRequest(), 1L, 1L);
    }

    @Test
    void create() {
        PostCategory postCategory = PostCategory.create(100L, post);

        assertThat(postCategory.getCategoryCode()).isEqualTo(100L);
        assertThat(postCategory.getPost()).isEqualTo(post);
    }

}