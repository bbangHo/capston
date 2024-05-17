package com.example.capstone.aws.s3;

import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Import(AmazonS3MockConfig.class)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
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
    public void testS3Mock() {
//        assertThat(s3Mock).isInstanceOf(S3Mock.class);
    }

    @Test
    void upload() throws IOException {
        // given
        String path = "test.png";
        String contentType = "image/png";
        String dirName = "test";

        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        // when
        String urlPath = amazonS3Util.uploadFile(amazonS3Util.generateTestImagePath(UUID.randomUUID()), file);

        // then
        assertThat(urlPath).contains(path);
        assertThat(urlPath).contains(dirName);
    }
}

