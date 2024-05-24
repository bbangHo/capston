package com.example.capstone.post.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.security.dto.MemberSecurityDTO;
import com.example.capstone.post.dto.PostRequestDTO;
import com.example.capstone.post.dto.PostResponseDTO;
import com.example.capstone.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/{post-id}")
    public ApiResponse<PostResponseDTO.Post> getPost(@PathVariable(name = "post-id") Long postId) {

        PostResponseDTO.Post response = postService.getPost(postId);
        return ApiResponse.of(SuccessStatus._OK_GET_POST, response);
    }

    @PostMapping("/auth/posts")
    public ApiResponse<PostResponseDTO.Post> createPost(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO,
                                                        @RequestPart PostRequestDTO.PostUpload request,
                                                        @Size(max = 10) @RequestPart(required = false) List<MultipartFile> files) {
        Long memberId = memberSecurityDTO.getId();
        PostResponseDTO.Post response = postService.createPost(memberId, request, files);
        return ApiResponse.of(SuccessStatus._OK_CREATE_POST, response);
    }

    @PatchMapping("/auth/posts/{post-id}")
    public ApiResponse<PostResponseDTO.Post> updatePost(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO,
                                                        @PathVariable(name = "post-id") Long postId,
                                                        @ModelAttribute PostRequestDTO.PostUpload request,
                                                        @Size(max = 10) @RequestPart(required = false) List<MultipartFile> files) {
        Long memberId = memberSecurityDTO.getId();
        PostResponseDTO.Post response = postService.updatePost(memberId, postId, request, files);
        return ApiResponse.of(SuccessStatus._OK_UPDATE_POST, response);
    }

    @DeleteMapping("/auth/posts/{post-id}")
    public ApiResponse<PostResponseDTO.Delete> deletePost(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO,
                                                          @PathVariable(name = "post-id") Long postId) {
        Long memberId = memberSecurityDTO.getId();
        PostResponseDTO.Delete response = postService.deletePost(memberId, postId);
        return ApiResponse.of(SuccessStatus._OK_DELETE_POST, response);
    }

}
