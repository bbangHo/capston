package com.example.capstone.aws.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.capstone.config.AmazonConfig;
import io.findify.s3mock.S3Mock;
import lombok.Getter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@Getter
public class AmazonS3MockConfig {
    public String bucket = "capstone-image-s3";

    private String region = "ap-northeast-2";

    @Bean
    public S3Mock s3Mock() {
        return new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
    }

    @Bean
    @Primary
    public AmazonS3 amazonS3(S3Mock s3Mock) {
        s3Mock.start();

        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder
                .EndpointConfiguration("http://localhost:8001", region);

        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        client.createBucket(bucket);

        return client;
    }

    @Bean
    @Primary
    public AmazonConfig amazonConfig() {
        AmazonConfig config = new AmazonConfig();
        config.setAccessKey("testAccessKey");
        config.setSecretKey("testSecretKey");
        config.setRegion("us-west-2");
        config.setBucket("capstone-image-s3");
        config.setItemDir("items");
        config.setItemPreviewDir("item_previews");
        config.setSellerImage("seller_image");
        return config;
    }

    @Bean
    @Primary
    public AmazonS3Util amazonS3Util(AmazonS3 amazonS3, AmazonConfig amazonConfig) {
        return new AmazonS3Util(amazonS3, amazonConfig);
    }
}