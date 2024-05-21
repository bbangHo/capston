package com.example.capstone.seller.service;

import com.example.capstone.aws.s3.AmazonS3Util;
import com.example.capstone.common.ValidateUtil;
import com.example.capstone.member.Member;
import com.example.capstone.seller.Post;
import com.example.capstone.seller.PostImage;
import com.example.capstone.seller.converter.PostConverter;
import com.example.capstone.seller.dto.PostRequestDTO;
import com.example.capstone.seller.dto.PostResponseDTO;
import com.example.capstone.seller.repository.PostImageRepository;
import com.example.capstone.seller.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final AmazonS3Util amazonS3Util;
    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;
    private final ValidateUtil validateUtil;

    @Override
    public PostResponseDTO.PostPreviews getPostPreviews(Long memberId) {
        return null;
    }

    @Override
    public PostResponseDTO.Post getPost(Long postId) {
        Post post = validateUtil.validPost(postId);
        return PostConverter.toPost(post);
    }

    @Override
    public PostResponseDTO.Post createPost(Long memberId, PostRequestDTO.PostUpload request) {
        Member member = validateUtil.validMember(memberId);

        Post post = Post.builder()
                .content(request.getContent())
                .build();

        post.addMember(member);
        post = postRepository.save(post);

        for(MultipartFile file : request.getMultipartFileList()) {
            String path = amazonS3Util.generateItemImagePath();
            String url = amazonS3Util.uploadFile(path, file);

            PostImage postImage = PostImage.builder()
                    .imageUrl(url)
                    .post(post)
                    .build();

            postImage = postImageRepository.save(postImage);
            post.addImage(postImage);
        }

        return PostConverter.toPost(post);
    }

    @Override
    public PostResponseDTO.Post updatePost(Long memberId, Long postId, PostRequestDTO.PostUpload request) {
//        Member member = validateUtil.validMember(memberId);
//        Post post = validateUtil.validPost(postId);
//
//        validateUtil.isAuthor(member, post);
//
//        for (MultipartFile file : request.getMultipartFileList()) {
//
//        }

        return null;
    }

    @Override
    public void deletePost(Long memberId, Long postId) {
        Member member = validateUtil.validMember(memberId);
        Post post = validateUtil.validPost(postId);

        validateUtil.isAuthor(member, post);

        postImageRepository.deleteAll(post.getPostImages());
    }
}
