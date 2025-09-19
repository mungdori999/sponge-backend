package com.mungdori.sponge.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mungdori.sponge.domain.post.PostFixture.createPostCreateRequest;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostFileTest {

    Post post;

    @BeforeEach
    void setUp() {
        post = Post.create(createPostCreateRequest(), 1L, 1L);
    }


    @Test
    void create() {
        PostFile postFile = PostFile.create("test-url.com", post);

        assertThat(postFile.getFileUrl()).isEqualTo("test-url.com");
        assertThat(postFile.getPost()).isEqualTo(post);
    }

}