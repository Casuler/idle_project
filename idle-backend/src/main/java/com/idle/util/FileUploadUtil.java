package com.idle.util;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUploadUtil {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.dir.prefix}")
    private String prefix;

    public String upload(MultipartFile file){
        try{
            InputStream inputStream = file.getInputStream();
            String originFileName = file.getOriginalFilename();
            String uuidFileName = cn.hutool.core.lang.UUID.randomUUID().toString(true) + originFileName;
            String dateTime = DateTime.now().toString("yyyy-MM-dd");
            String realFileName = dateTime + uuidFileName;
            String dirFileName = prefix + realFileName;
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpg");

            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            ossClient.putObject(bucketName, dirFileName, inputStream, meta);

            ossClient.shutdown();

            String url = "https://" + bucketName + "." + endpoint + "/" + dirFileName;
            return url;
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
