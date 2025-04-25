package com.hlh.hlhoj.controller;

import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.service.StudentQuestionSubmitService;
import com.hlh.hlhoj.service.TeacherQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import java.io.File;

@RestController
@RequestMapping("/teacherQuestion")
@Slf4j
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
public class FileDownLoadControl {

    @Autowired
    private TeacherQuestionService teacherQuestionService;
    @Autowired
    private StudentQuestionSubmitService studentQuestionSubmitService;

    @GetMapping("/download/{questionId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long questionId, @RequestParam String fileName){
        TeacherQuestion question = teacherQuestionService.getById(questionId);
        if(question==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String teacherBase="D/FileCache/teacherQuestion/";
        String textPath = teacherBase+question.getTextPath();
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
