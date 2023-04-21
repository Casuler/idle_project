package com.idle.controller;

import com.idle.service.impl.AliyunOssServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/oss")
public class FileUpload {
    @Resource
    private AliyunOssServiceImpl aliyunOssServiceImpl;

    @PostMapping("/upload")
    public Map<String, Object> uploadOssFile(MultipartFile file){
        return this.aliyunOssServiceImpl.upload(file);
    }
}
