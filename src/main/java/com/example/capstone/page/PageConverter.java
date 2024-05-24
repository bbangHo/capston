package com.example.capstone.page;

import com.example.capstone.member.Member;
import com.example.capstone.post.Post;
import com.example.capstone.seller.Seller;

import java.util.List;

public class PageConverter {

    public static PageResponseDTO.PostPreview toPostPreview(Post post) {
        boolean empty = post.getPostImageList().isEmpty();

        return PageResponseDTO.PostPreview.builder()
                .postId(post.getId())
                .imageUrl(empty ? "" : post.getPostImageList().get(0).getImageUrl())
                .build();
    }

    public static PageResponseDTO.PostPreviews toPostPreviews(List<Post> postList, Member member) {
        List<PageResponseDTO.PostPreview> postPreviewList = postList.stream()
                .map(PageConverter::toPostPreview)
                .toList();

        Seller seller = member.getSeller();

        return PageResponseDTO.PostPreviews.builder()
                .memberId(member.getId())
                .memberImageUrl(seller.getImageUrl())
                .memberName(member.getName())
                .simpleIntro(seller.getIntroduction())
                .detailIntro(seller.getDetails())
                .numberPosts(member.getPostList().size())
                .postPreviews(postPreviewList)
                .build();
    }
}
