package com.mungdori.sponge.application.post.required;

import com.mungdori.sponge.domain.post.Post;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PostRepository extends Repository<Post,Long> {

    Post save(Post post);

    Optional<Post> findById(Long id);

}
