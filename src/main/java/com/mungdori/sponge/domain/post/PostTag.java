package com.mungdori.sponge.domain.post;

import com.mungdori.sponge.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag extends AbstractEntity {

    @Column(name = "has_tag", nullable = false)
    private String hasTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    public static PostTag create(String hasTag, Post post) {
        PostTag postTag = new PostTag();

        postTag.hasTag = hasTag;
        postTag.post = post;

        return postTag;
    }
}
