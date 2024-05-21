package com.example.capstone.seller.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.security.dto.MemberSecurityDTO;
import com.example.capstone.seller.dto.PostRequestDTO;
import com.example.capstone.seller.dto.PostResponseDTO;
import com.example.capstone.seller.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{post-id}")
    public ApiResponse<PostResponseDTO.Post> deletePost(@PathVariable(name = "post-id") Long postId) {
        PostResponseDTO.Post response = postService.getPost(postId);
        return ApiResponse.of(SuccessStatus._OK_GET_POST, response);
    }

    @PostMapping
    public ApiResponse<PostResponseDTO.Post> deletePost(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO,
                                                        @Valid PostRequestDTO.PostUpload request) {
        Long memberId = memberSecurityDTO.getMemberId();
        PostResponseDTO.Post response = postService.createPost(memberId, request);
        return ApiResponse.of(SuccessStatus._OK_CREATE_POST, response);
    }

    @PatchMapping("/{post-id}")
    public ApiResponse<PostResponseDTO.Post> deletePost(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO,
                                                        @PathVariable(name = "post-id") Long postId,
                                                        @Valid PostRequestDTO.PostUpload request) {
        Long memberId = memberSecurityDTO.getMemberId();
        PostResponseDTO.Post response = postService.updatePost(memberId, postId, request);
        return ApiResponse.of(SuccessStatus._OK_UPDATE_POST, response);
    }

    @DeleteMapping("/{post-id}")
    public ApiResponse<Boolean> deletePost(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO,
                                    @PathVariable(name = "post-id") Long postId) {
        Long memberId = memberSecurityDTO.getMemberId();
        postService.deletePost(memberId, postId);
        return ApiResponse.of(SuccessStatus._OK_DELETE_POST, true);
    }

}
