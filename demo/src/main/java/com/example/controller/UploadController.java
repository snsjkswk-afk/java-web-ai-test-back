package com.example.controller;

import com.example.pojo.Result;
import com.example.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliOssUtil aliOssUtil;
   /* @PostMapping("/upload")本地文件上传*/
    /*public Result upLoad(String name, Integer age, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("文件上传");
        String originalFilename = file.getOriginalFilename();
        String newFilename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename = newFilename+extension;
        file.transferTo(new File("D:/images/" + originalFilename));
        return Result.success();
    }*/
    @PostMapping("/upload")
    public Result upLoad(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("文件上传");
        String url = aliOssUtil.upload(file.getBytes(), file.getOriginalFilename());
        return Result.success(url);

    }
}
