package com.mungdori.sponge.domain.post;

import com.mungdori.sponge.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class PostFile extends AbstractEntity {

    @Column(name = "file_url",nullable = false)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static PostFile create(String fileUrl,Post post) {
        PostFile postFile = new PostFile();

        postFile.fileUrl = fileUrl;
        postFile.post = post;

        return postFile;
    }


}
