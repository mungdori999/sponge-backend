package com.mungdori.sponge.domain.post;

import com.mungdori.sponge.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends AbstractEntity {

    @Column(name = "title", nullable = false)
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

    @Column(name = "pet_id")
    private Long petId;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCategory> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> tagList = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostFile> fileList = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static Post create(PostCreateRequest createRequest, Long petId) {
        Post post = new Post();

        post.title = requireNonNull(createRequest.title());
        post.content = requireNonNull(createRequest.content());
        post.duration = requireNonNull(createRequest.duration());

        post.petId = petId;

        createRequest.categoryCodeList().forEach((categoryCode) -> {
            post.categoryList.add(PostCategory.create(categoryCode, post));
        });

        createRequest.hashTagList().forEach((hashTag) -> {
            post.tagList.add(PostTag.create(hashTag, post));
        });

        createRequest.fileUrlList().forEach((fileUrl) -> {
            post.fileList.add(PostFile.create(fileUrl, post));
        });

        post.createdAt = LocalDateTime.now();
        return post;

    }

    public Post update(PostInfoUpdateRequest updateRequest) {
        this.title = requireNonNull(updateRequest.title());
        this.content = requireNonNull(updateRequest.content());
        this.duration = requireNonNull(updateRequest.duration());

        categoryList.clear();
        tagList.clear();
        fileList.clear();

        updateRequest.categoryCodeList().forEach((categoryCode) -> {
            this.categoryList.add(PostCategory.create(categoryCode, this));
        });

        updateRequest.hashTagList().forEach((hashTag) -> {
            this.tagList.add(PostTag.create(hashTag, this));
        });

        updateRequest.fileUrlList().forEach((fileUrl) -> {
            this.fileList.add(PostFile.create(fileUrl, this));
        });

        this.updatedAt = LocalDateTime.now();

        return this;
    }

}
