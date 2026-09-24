package com.example.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
public class AliOssUtil {

    @Autowired
    private AliyunProperity aliyunProperity;

   /* // 只需要配置这两个
    @Value("${aliyun.oss.endpoint}")
    private  String endpoint;
    @Value("${aliyun.oss.bucketName}")
    private  String bucketName;
    @Value("${aliyun.oss.region}")
    private  String region;*/

    /**
     * 字节数组上传（使用官方 Environment 环境变量凭证）
     */
    public String upload(byte[] content, String fileName) {

        // ✅ 你要的：官方环境变量凭证提供器（自动读取 ALIBABA_CLOUD_ACCESS_KEY_ID / SECRET）
        CredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();

        // ✅ 官方推荐构建方式
        OSS ossClient = new OSSClientBuilder()
                .build(aliyunProperity.getEndpoint(), credentialsProvider);

        try {
            // 字节数组转流上传
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
            PutObjectRequest request = new PutObjectRequest(aliyunProperity.getBucketName(), fileName, inputStream);
            ossClient.putObject(request);

            return "https://" + aliyunProperity.getBucketName() + "." + aliyunProperity.getEndpoint() + "/" + fileName;
        } finally {
            ossClient.shutdown();
        }
    }
}