package com.lbj.pochi.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfig {

    /**
     * oss对外访问域名
     */
    private String endpoint;

    /**
     * 访问身份用户标识
     */
    private String accessKeyId;

    /**
     * 用户用于加密签名字符串和oss用来验证签名字符串的密钥
     */
    private String accessKeySecret;

    /**
     * oss的存储空间
     */
    private String bucketName;

    /**
     * 预览url
     */
    private String viewUrl;

    /**
     * 最大文件大小，单位M
     */
    private Integer maxSize;

    /**
     * 上传的目录
     */
    private List<String> uploadDirs;

    /**
     * 上传后缀
     */
    private List<String> imageTypes;

    @Bean
    public OSS oss() {
        OSSClientBuilder builder = new OSSClientBuilder();
        return builder.build(endpoint, accessKeyId, accessKeySecret);
    }
}
