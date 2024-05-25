package com.example.capstone.post.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.aws.s3.AmazonS3Util;
import com.example.capstone.common.QueryService;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.member.Member;
import com.example.capstone.post.Post;
import com.example.capstone.post.PostImage;
import com.example.capstone.post.converter.PostConverter;
import com.example.capstone.post.dto.PostRequestDTO;
import com.example.capstone.post.dto.PostResponseDTO;
import com.example.capstone.post.repository.PostImageRepository;
import com.example.capstone.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final AmazonS3Util amazonS3Util;
    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;
    private final QueryService queryService;

    @Override
    public PostResponseDTO.Post getPost(Long postId) {
        Post post = queryService.findPost(postId);
        return PostConverter.toPost(post);
    }

    @Override
    public PostResponseDTO.Post createPost(Long memberId, PostRequestDTO.PostUpload request, List<MultipartFile> files) {
        Member member = queryService.findMember(memberId);

        Post post = Post.builder()
                .content(request.getContent())
                .build();

        post.addMember(member);
        post = postRepository.save(post);

        //TODO: 이미지 1건 저장하는데 2.5초 정도 걸림. 비동기 처리 or uploadFile()만 스레드로 실행?
        for (MultipartFile file : files) {
            uploadImage(post, file);
        }

        return PostConverter.toPost(post);
    }

    @Override
    public PostResponseDTO.Post updatePost(Long memberId, Long postId, PostRequestDTO.PostUpload request, List<MultipartFile> files) {
        Member member = queryService.findMember(memberId);
        Post post = queryService.findPost(postId);

        isAuthor(member, post);
//        updatePostImages(post, files);
        post = postRepository.save(post);

        return PostConverter.toPost(post);
    }

    @Override
    public PostResponseDTO.Delete deletePost(Long memberId, Long postId) {
        Member member = queryService.findMember(memberId);
        Post post = queryService.findPost(postId);

        if (isAuthor(member, post)) {
            postImageRepository.deleteAll(post.getPostImageList());
            postRepository.delete(post);
            return PostConverter.toDelete(post, true);
        }

        return PostConverter.toDelete(post, false);
    }

    /**
     * member가 작성한 post인지 검증하는 메소드
     */
    private Boolean isAuthor(Member member, Post post) {
        if (!member.getId().equals(post.getMember().getId())) {
            throw new GeneralException(ErrorStatus.POST_FORBIDDEN);
        }

        return true;
    }

    // post에 이미지를 업로드 하는 메소드
    private void uploadImage(Post post, MultipartFile file) {
        PostImage postImage = postImageBuilder(post, file);
        postImage = postImageRepository.save(postImage);
        post.addImage(postImage);
    }

    private PostImage postImageBuilder(Post post, MultipartFile file) {
        String path = amazonS3Util.generateItemImagePath(file);
        String url = amazonS3Util.uploadFile(path, file);

        return PostImage.builder()
                .imageUrl(url)
                .originalFileName(file.getOriginalFilename())
                .post(post)
                .build();
    }
}
