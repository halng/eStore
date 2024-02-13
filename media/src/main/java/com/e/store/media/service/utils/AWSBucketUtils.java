package com.e.store.media.service.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AWSBucketUtils extends UploadToBucketUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(GCPBucketUtils.class);

  @Value("${aws.access.secret}")
  private String awsSecret;

  @Value("${aws.access.key}")
  private String awsKey;

  @Value("${aws.bucket.name}")
  private String awsBucket;

  @Value("${aws.bucket.region}")
  private String awsRegion;

  private String endPointUrl;
  private AmazonS3 amazonS3;

  public AWSBucketUtils() {}

  AmazonS3 getAmazonS3CLI() {
    AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(awsRegion)
        .build();
  }

  private File convertToFile(String fileName, MultipartFile multipartFile) throws IOException {
    File file =
        File.createTempFile(fileName, getFileExtension(multipartFile.getOriginalFilename()));

    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(multipartFile.getBytes());
    } catch (Exception e) {
      LOGGER.error("Error when converting Multipart to file with error msg: {}", e.getMessage());
    }

    return file;
  }

  @Override
  public String uploadFile(MultipartFile multipartFile, String fileName, String contentType) {
    String fileUrl = "";
    try {
      String finalFileName = fileName + getFileExtension(multipartFile.getOriginalFilename());
      fileUrl = endPointUrl + "/" + awsBucket + "/" + finalFileName;
      File file = convertToFile(fileName, multipartFile);
      amazonS3 = getAmazonS3CLI();
      TransferManager transferManager =
          TransferManagerBuilder.standard()
              .withS3Client(amazonS3)
              .withMultipartUploadThreshold((long) (50 * 1024 * 1025))
              .build();

      long start = System.currentTimeMillis();
      Upload result = transferManager.upload(awsBucket, finalFileName, file);
      result.waitForCompletion();
      long end = System.currentTimeMillis();
      LOGGER.info("Complete Multipart Uploading {}s", (end - start) / 1000);

    } catch (IOException | InterruptedException e) {
      LOGGER.error("Can not upload media file with error: {}", e.getMessage());
    }
    return fileUrl;
  }
}
