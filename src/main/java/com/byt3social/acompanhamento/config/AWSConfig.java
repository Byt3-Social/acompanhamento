package com.byt3social.acompanhamento.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AWSConfig {
    @Value("${com.byt3social.aws.access-key}")
    private String accessKey;
    @Value("${com.byt3social.aws.secret-key}")
    private String secretKey;

    private StaticCredentialsProvider credenciais() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
    }

    @Bean
    public S3Client amazonS3() {
        return S3Client
                .builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(credenciais())
                .build();
    }

    @Bean
    public S3Presigner presigner() {
        return S3Presigner
                .builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(credenciais())
                .build();
    }
}
