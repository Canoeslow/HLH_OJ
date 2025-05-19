package com.hlh.hlhoj.controller;


import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.service.StudentQuestionSubmitService;
import com.hlh.hlhoj.service.TeacherQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/StudentQuestion")
@Slf4j
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
public class FileDownLoadUserControl {

    @Autowired
    private TeacherQuestionService teacherQuestionService;
    @Autowired
    private StudentQuestionSubmitService studentQuestionSubmitService;

    @GetMapping("/download/{questionId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long questionId, @RequestParam String fileName) {
        TeacherQuestion question = teacherQuestionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "实验任务不存在");
        }

        String teacherBase = "D:/FileCache/studentSubmit/";  // ✅ 注意路径拼接用 /，不是 D/FileCache
        String textPath = teacherBase + question.getTextPath();
        File file = new File(textPath);
        if (!file.exists()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件不存在");
        }

        FileSystemResource fileSystemResource = new FileSystemResource(file);

        // ✅ 文件名转码（防止中文乱码）
        String encodedFileName;
        try {
            encodedFileName = java.net.URLEncoder.encode(file.getName(), "UTF-8");
        } catch (Exception e) {
            encodedFileName = file.getName();  // 回退
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(file.length())
                .body(fileSystemResource);
    }

}
