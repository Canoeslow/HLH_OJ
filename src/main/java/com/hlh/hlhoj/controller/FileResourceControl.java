package com.hlh.hlhoj.controller;

import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/TeacherResource")
@Slf4j
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
public class FileResourceControl {

    @javax.annotation.Resource
    private ResourceService resourceService;
    @GetMapping("/download/{questionId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long questionId, @RequestParam String fileName){
        com.hlh.hlhoj.model.entity.Resource resource = resourceService.getById(questionId);
        if(resource==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String teacherBase="D/FileCache/Resource/";
        String textPath = teacherBase+resource.getTextpath();
        File file = new File(textPath);
        if(!file.exists()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileSystemResource);
    }
}
