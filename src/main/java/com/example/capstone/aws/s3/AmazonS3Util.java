package com.example.capstone.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.config.AmazonConfig;
import com.example.capstone.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Util {

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    /**
     * aws s3의 '/{keyName}' 경로에 file을 저장하는 메소드
     *
     * @param file 요청으로 받은 MultipartFile
     * @return url
     */
    public String uploadFile(String path, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), path, file.getInputStream(), metadata));
        } catch (IOException e) {
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
            throw new GeneralException(ErrorStatus.IMAGE_UPLOAD_ERROR);
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), path).toString();
    }

    public String generateItemImagePath(MultipartFile file) {
        StringBuilder sb = new StringBuilder();

        sb.append(amazonConfig.getItemDir());
        sb.append('/');
        sb.append(UUID.randomUUID());
        sb.append('.');
        sb.append(StringUtils.getFilenameExtension(file.getOriginalFilename()));

        return sb.toString();
    }

    private String generateItemPreviewImagePath(UUID uuid) {
        return amazonConfig.getItemPreviewDir() + '/' + UUID.randomUUID();
    }

    public String generateTestImagePath(UUID uuid) {
        return "/test" + '/' + UUID.randomUUID();
    }
}