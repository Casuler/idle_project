package com.idle.service.impl;

import com.idle.service.AliyunOssService;
import com.idle.util.FileUploadUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service("aliyunOssService")
public class AliyunOssServiceImpl implements AliyunOssService {

    @Resource
    private FileUploadUtil fileUploadUtil;

    @Override
    public Map<String, Object> upload(MultipartFile file){
        String path = "picture";
        Map<String, Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","上传成功");
        map.put("data", this.fileUploadUtil.upload(file));
        return map;
    }

}
