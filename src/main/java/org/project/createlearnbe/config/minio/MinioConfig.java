package org.project.createlearnbe.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

  private final MinioProperties properties;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
        .endpoint(properties.getUrl())
        .credentials(properties.getAccessKey(), properties.getSecretKey())
        .build();
  }

  @Bean
  public CommandLineRunner initBucket(MinioClient minioClient) {
    return args -> {
      String bucket = properties.getBucket();
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
      if (!found) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
      }

      // Set bucket policy to public
      String policyJson =
          """
                    {
                      "Version":"2012-10-17",
                      "Statement":[
                        {
                          "Effect":"Allow",
                          "Principal":"*",
                          "Action":["s3:GetObject"],
                          "Resource":["arn:aws:s3:::%s/*"]
                        }
                      ]
                    }
                    """
              .formatted(bucket);

      minioClient.setBucketPolicy(
          SetBucketPolicyArgs.builder().bucket(bucket).config(policyJson).build());
    };
  }
}
