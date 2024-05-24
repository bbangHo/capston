package com.example.capstone.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class PostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private Long postId;
        private List<String> imageUrlList;
        private String content;
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Delete {
        private Long postId;
        private Boolean isDelete;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPreview {
        private Long postId;
        private String imageList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostPreviews {
        private Long memberId;
        private String memberImageUrl;
        private String memberName;
        private String simpleIntro;
        private String detailIntro;
        private Integer numberPosts;
        private List<PostPreview> postPreviews;
    }
}
