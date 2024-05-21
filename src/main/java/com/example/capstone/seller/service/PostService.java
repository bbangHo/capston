package com.example.capstone.seller.service;

import com.example.capstone.seller.dto.PostResponseDTO;

public interface PostService {

    PostResponseDTO.PostPreviews getPostPreviews();
    PostResponseDTO.Post getPost();
    PostResponseDTO.Post createPost();
    PostResponseDTO.Post updatePost();
    void deletePost();
}
