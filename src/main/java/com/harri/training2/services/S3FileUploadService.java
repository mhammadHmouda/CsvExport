package com.harri.training2.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service
public class S3FileUploadService {
    private final AmazonS3 amazonS3;

    @Autowired
    public S3FileUploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadCsvToS3(String bucketName, String fileName, String csvData) {
        byte[] contentBytes = csvData.getBytes();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentBytes.length);
        metadata.setContentType("text/csv");

        amazonS3.putObject(bucketName, fileName, new ByteArrayInputStream(contentBytes), metadata);
    }
}