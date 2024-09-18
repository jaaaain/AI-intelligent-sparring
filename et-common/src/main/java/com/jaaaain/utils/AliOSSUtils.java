package com.jaaaain.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyuncs.exceptions.ClientException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云 OSS 工具类
 */

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSUtils {

    private String endpoint;
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;
    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        //1 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();

        //2 获取原始文件名，使用UUID生成随机字符串，组装唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //3 上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);

        //4 获取文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        //5 关闭ossClient客户端，返回文件在OSS存储空间的地址
        ossClient.shutdown();
        return url;
    }

    public EnvironmentVariableCredentialsProvider getCredentialsProvider() {
        try {
            return CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }
}