package com.mungdori.sponge.domain.post;

import com.mungdori.sponge.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategory extends AbstractEntity {

    @Column(name = "category_code", nullable = false)
    private Long categoryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    public static PostCategory create(Long categoryCode, Post post) {
        PostCategory postCategory = new PostCategory();

        postCategory.categoryCode = categoryCode;
        postCategory.post = post;

        return postCategory;
    }


}
