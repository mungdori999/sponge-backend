package com.mungdori.sponge.application.post;

import com.mungdori.sponge.application.owner.provided.OwnerFinder;
import com.mungdori.sponge.application.pet.provided.PetFinder;
import com.mungdori.sponge.application.pet.required.PetRepository;
import com.mungdori.sponge.application.post.provided.PostFinder;
import com.mungdori.sponge.application.post.provided.PostManager;
import com.mungdori.sponge.application.post.required.PostRepository;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.post.Post;
import com.mungdori.sponge.domain.post.PostCreateRequest;
import com.mungdori.sponge.domain.post.PostInfoUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class PostModifyService implements PostManager {

    private final PostRepository postRepository;
    private final PetFinder petFinder;
    private final OwnerFinder ownerFinder;
    private final PostFinder postFinder;

    @Override
    public Post create(PostCreateRequest postCreateRequest, Long petId, String email) {

        checkValidMyAccount(petId, email);

        Post post = Post.create(postCreateRequest, petId);

        return postRepository.save(post);
    }

    @Override
    public Post update(Long postId, PostInfoUpdateRequest postInfoUpdateRequest, Long petId, String email) {

        checkValidMyAccount(petId, email);

        Post post = postFinder.find(postId);

        post.update(postInfoUpdateRequest);

        post = postRepository.save(post);

        return post;
    }

    private void checkValidMyAccount(Long petId, String email) {
        Pet pet = petFinder.find(petId);
        Owner owner = ownerFinder.findByEmail(email);

        if (!pet.getOwnerId().equals(owner.getId())) {
            throw new IllegalArgumentException("자신의 계정이 아닙니다!");
        }
    }
}
