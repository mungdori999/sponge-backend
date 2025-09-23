package com.mungdori.sponge.application.post.required;

import com.mungdori.sponge.domain.post.Post;
import com.mungdori.sponge.domain.post.PostFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
record PostRepositoryTest(PostRepository postRepository, EntityManager entityManager) {

    @Test
    void createPost() {
        Post post = Post.create(PostFixture.createPostCreateRequest(), 1L);

        assertThat(post.getId()).isNull();

        postRepository.save(post);

        assertThat(post.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        var found = postRepository.findById(post.getId()).orElseThrow();

        assertThat(found.getTitle()).isEqualTo(post.getTitle());
        assertThat(found.getFileList()).containsExactlyInAnyOrderElementsOf(post.getFileList());
        assertThat(found.getTagList()).containsExactlyInAnyOrderElementsOf(post.getTagList());
        assertThat(found.getFileList()).containsExactlyInAnyOrderElementsOf(post.getFileList());

    }

}