package com.mungdori.sponge.application.post;

import com.mungdori.sponge.application.post.provided.PostFinder;
import com.mungdori.sponge.application.post.required.PostRepository;
import com.mungdori.sponge.domain.post.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostQueryService implements PostFinder {

    private final PostRepository postRepository;

    @Override
    public Post find(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("No post found with id: " + postId));

    }
}
