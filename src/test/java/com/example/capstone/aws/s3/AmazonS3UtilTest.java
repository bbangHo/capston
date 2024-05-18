//package com.example.capstone.aws.s3;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectResult;
//import com.example.capstone.config.AmazonConfig;
//import io.findify.s3mock.S3Mock;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
//@Import(AmazonS3MockConfig.class)
//@TestPropertySource("classpath:application-test.yml")
//@ActiveProfiles("test")
//@SpringBootTest
//class AmazonS3UtilTest {
//    @Autowired
//    private S3Mock s3Mock;
//
//    @Autowired
//    private AmazonS3Util amazonS3Util;
//
//    @MockBean
//    private AmazonS3 amazonS3; // MockBean으로 설정
//
//    @AfterEach
//    public void tearDown() {
//        s3Mock.stop();
//    }
//
//    @BeforeEach
//    void beforeEach() throws MalformedURLException {
//        // Mock 설정
//        given(amazonS3.putObject(any(PutObjectRequest.class))).willReturn(new PutObjectResult());
//        given(amazonS3.getUrl(any(), any())).willReturn(new URL("http://localhost:8001/test.png"));
//    }
//
//    @Test
//    void upload() throws IOException {
//        // given
//        String path = "test.png";
//        String contentType = "image/png";
//        String dirName = "test";
//
//        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());
//
//        // when
//        String urlPath = amazonS3Util.uploadFile(amazonS3Util.generateTestImagePath(UUID.randomUUID()), file);
//
//        // then
//        assertThat(urlPath).contains(path);
//        assertThat(urlPath).contains(dirName);
//    }
//}
