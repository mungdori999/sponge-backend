package com.mungdori.sponge.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.post.PostFixture.createPostCreateRequest;
import static com.mungdori.sponge.domain.post.PostFixture.createPostInfoRequest;
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
        assertThat(post.getCategoryList()).hasSizeGreaterThan(1);
        assertThat(post.getTagList()).hasSizeGreaterThan(1);
        assertThat(post.getFileList()).hasSizeGreaterThan(1);
        assertThat(post.getCreatedAt()).isNotNull();
    }

    @Test
    void update() {
        var request = createPostInfoRequest();
        Post update = post.update(request);

        assertThat(update.getTitle()).isEqualTo(request.title());
        assertThat(update.getCategoryList()).hasSizeGreaterThan(1);
        assertThat(update.getTagList()).hasSizeGreaterThan(1);
        assertThat(update.getFileList()).hasSizeGreaterThan(1);
        assertThat(update.getUpdatedAt()).isNotNull();
    }



}