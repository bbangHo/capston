package com.example.capstone.seller.service;

import com.example.capstone.seller.dto.PostRequestDTO;
import com.example.capstone.seller.dto.PostResponseDTO;

public interface PostService {

    PostResponseDTO.PostPreviews getPostPreviews(Long memberId);
    PostResponseDTO.Post getPost(Long postId);
    PostResponseDTO.Post createPost(Long memberId, PostRequestDTO.PostUpload request);
    PostResponseDTO.Post updatePost(Long memberId, Long postId, PostRequestDTO.PostUpload request);
    void deletePost(Long memberId, Long postId);
}
