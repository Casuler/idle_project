package com.idle.controller;

import com.idle.entity.RestBean;
import com.idle.util.GithubUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/github")
public class UploadController {

    @Autowired
    private GithubUploader githubUploader;

    @PostMapping("upload")
    public RestBean<?> upload (@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return RestBean.success(this.githubUploader.upload(multipartFile));
    }
}