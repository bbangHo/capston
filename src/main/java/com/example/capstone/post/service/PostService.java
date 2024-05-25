package com.example.capstone.post.service;

import com.example.capstone.post.dto.PostRequestDTO;
import com.example.capstone.post.dto.PostResponseDTO;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponseDTO.Post getPost(Long postId);
    PostResponseDTO.Post createPost(Long memberId, PostRequestDTO.PostUpload request, List<MultipartFile> files);
    PostResponseDTO.Post updatePost(Long memberId, Long postId, PostRequestDTO.PostUpload request, List<MultipartFile> files);
    PostResponseDTO.Delete deletePost(Long memberId, Long postId);
}
