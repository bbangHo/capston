package com.example.capstone.aws.s3;

import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AmazonS3MockConfig.class)
@TestPropertySource("classpath:application-test.yml")
@ActiveProfiles("test")
@SpringBootTest
class AmazonS3UtilTest {
    @Autowired
    private S3Mock s3Mock;

    @Autowired
    private AmazonS3Util amazonS3Util;

    @AfterEach
    public void tearDown() {
        s3Mock.stop();
    }

    @Test
    void upload() throws IOException {
        // given
        String path = "test.png";
        String contentType = "image/png";
        String dirName = "test";
        UUID uuid = UUID.randomUUID();

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        // when
        String urlPath = amazonS3Util.uploadFile(amazonS3Util.generateTestImagePath(uuid), file);

        // then
        assertThat(urlPath).contains(uuid.toString());
        assertThat(urlPath).contains(dirName);
    }
}
