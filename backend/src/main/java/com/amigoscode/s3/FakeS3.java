package com.amigoscode.s3;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.*;

public class FakeS3 implements S3Client {

    private static final String PATH = System.getProperty("user.home") + "/.amigoscode/s3";

    @Override
    public String serviceName() {
        return "fake";
    }

    @Override
    public void close() {

    }

    @Override
    public PutObjectResponse putObject(PutObjectRequest putObjectRequest, RequestBody requestBody)
            throws AwsServiceException, SdkClientException, S3Exception {
        InputStream inputStream = requestBody.contentStreamProvider().newStream();
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            FileUtils.writeByteArrayToFile(new File(buildObjectFullPath(
                            putObjectRequest.bucket(),
                            putObjectRequest.key())
                    ), bytes
            );
            return PutObjectResponse.builder().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseInputStream<GetObjectResponse> getObject(GetObjectRequest getObjectRequest)
            throws AwsServiceException, SdkClientException {
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(buildObjectFullPath(
                            getObjectRequest.bucket(),
                            getObjectRequest.key())
                    );
            return new ResponseInputStream<>(
                    GetObjectResponse.builder().build(),
                    fileInputStream
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildObjectFullPath(String bucketName, String key) {
        return PATH + "/" + bucketName + "/" + key;
    }
}