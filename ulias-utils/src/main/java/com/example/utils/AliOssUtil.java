package com.example.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
public class AliOssUtil {

    @Autowired
    private AliyunProperity aliyunProperity;

    /**
     * 启动时检查凭证是否加载成功，避免上传阶段才报看不懂的 SDK 错误
     */
    @PostConstruct
    public void checkCredentials() {
        String keyId = aliyunProperity.getAccessKeyId();
        String keySecret = aliyunProperity.getAccessKeySecret();
        if (StringUtils.hasText(keyId) && StringUtils.hasText(keySecret)) {
            log.info("OSS 配置加载成功：endpoint={}，bucket={}，accessKeyId={}****",
                    aliyunProperity.getEndpoint(), aliyunProperity.getBucketName(),
                    keyId.substring(0, Math.min(8, keyId.length())));
        } else {
            log.error("OSS 凭证为空！请在环境变量中配置 ALIYUN_OSS_ACCESS_KEY_ID / ALIYUN_OSS_ACCESS_KEY_SECRET（或 OSS_ACCESS_KEY_ID / OSS_ACCESS_KEY_SECRET）后重启应用，否则文件上传会失败");
        }
    }

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

        // 直接读取 yml 配置里的 accessKeyId / accessKeySecret
        if (!StringUtils.hasText(aliyunProperity.getAccessKeyId())
                || !StringUtils.hasText(aliyunProperity.getAccessKeySecret())) {
            throw new IllegalStateException("OSS 凭证未配置：请设置环境变量 ALIYUN_OSS_ACCESS_KEY_ID / ALIYUN_OSS_ACCESS_KEY_SECRET 后重启应用");
        }
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(
                aliyunProperity.getAccessKeyId(),
                aliyunProperity.getAccessKeySecret()
        );

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