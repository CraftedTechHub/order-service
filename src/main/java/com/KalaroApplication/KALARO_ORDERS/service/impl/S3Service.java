package com.KalaroApplication.KALARO_ORDERS.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private S3Client s3Client;

    @Autowired
    private String bucketName;

    public String uploadModelImage(MultipartFile file, String fileName) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("model-images/" + fileName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key("model-images/" + fileName)).toString();
    }

    public void deleteModelImage(String fileName) {
        try {
            s3Client.deleteObject(builder -> builder
                    .bucket(bucketName)
                    .key("model-images/" + fileName)
                    .build()
            );
        } catch (Exception e) {
            log.error("Failed to delete model image from S3", e.getMessage());
            throw e;
        }
    }
}
