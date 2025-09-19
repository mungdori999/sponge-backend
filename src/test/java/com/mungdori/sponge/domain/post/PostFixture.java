package com.mungdori.sponge.domain.post;

import java.util.List;

public class PostFixture {

    public static PostCreateRequest createPostCreateRequest() {
        return new PostCreateRequest("제목", "테스트 내용", "1년?", List.of(100L, 200L), List.of("태그1", "태그2"), List.of("img_url","img_url2"));
    }

    public static PostInfoUpdateRequest createPostInfoRequest() {
        return new PostInfoUpdateRequest("제목", "테스트 내용", "1년?", List.of(100L, 200L), List.of("태그1", "태그2"), List.of("img_url","img_url2"));
    }

}
