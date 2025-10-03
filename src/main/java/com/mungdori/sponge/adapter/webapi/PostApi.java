package com.mungdori.sponge.adapter.webapi;

import com.mungdori.sponge.adapter.security.utils.AuthorizationUtil;
import com.mungdori.sponge.adapter.webapi.dto.post.PostCreateResponse;
import com.mungdori.sponge.application.post.provided.PostManager;
import com.mungdori.sponge.domain.post.Post;
import com.mungdori.sponge.domain.post.PostCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostApi {

    private final PostManager postManager;
    private final AuthorizationUtil authorizationUtil;

    @PostMapping()
    public PostCreateResponse createPost(@RequestBody @Valid PostCreateRequest postCreateRequest, @RequestParam Long petId) {
        Post post = postManager.create(postCreateRequest, petId, authorizationUtil.getId());
        return PostCreateResponse.of(post);
    }
}
