package com.mungdori.sponge.domain.post;

import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.pet.Pet;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends AbstractEntity {

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;


    @Column(name = "duration", nullable = false, length = 150)
    private String duration;

    @Column(name = "like_count")
    private int likeCount = 0;

    @Column(name = "answer_count")
    private int answerCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static Post create(PostCreateRequest createRequest) {
        Post post = new Post();

        post.title = requireNonNull(createRequest.title());
        post.content = requireNonNull(createRequest.content());
        post.duration = requireNonNull(createRequest.duration());

        post.createdAt = LocalDateTime.now();
        return post;

    }
}
