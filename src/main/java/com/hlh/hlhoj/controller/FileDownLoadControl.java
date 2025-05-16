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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    public ResponseEntity<Resource> downloadFile(@PathVariable Long questionId, @RequestParam String fileName) {
        // 查询实验任务
        TeacherQuestion question = teacherQuestionService.getById(questionId);
        if (question == null || question.getTextPath() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "任务不存在或无文件路径");
        }

        // 构建文件路径
        String teacherBase = "D:\\\\FileCache\\\\teacherQuestion\\\\";
        String fullPath = teacherBase + question.getTextPath();
        File file = new File(fullPath);

        if (!file.exists()) {
            log.error("文件不存在: {}", fullPath);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件不存在");
        }

        // 创建资源对象
        FileSystemResource fileResource = new FileSystemResource(file);

        // 解析 MIME 类型
        MediaType mediaType = getMediaType(fileName);

        // 构建响应头
        HttpHeaders headers = new HttpHeaders();
        try {
            String encodedName = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedName + "\"");
        } catch (UnsupportedEncodingException e) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        }

        headers.setContentType(mediaType);
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION + "," + HttpHeaders.CONTENT_TYPE);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-store");

        log.info("开始下载文件: {}", file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(fileResource);
    }

    /**
     * 简易 MIME 类型判断
     */
    private MediaType getMediaType(String fileName) {
        if (fileName == null) return MediaType.APPLICATION_OCTET_STREAM;
        fileName = fileName.toLowerCase();
        if (fileName.endsWith(".pdf")) return MediaType.APPLICATION_PDF;
        if (fileName.endsWith(".doc")) return MediaType.valueOf("application/msword");
        if (fileName.endsWith(".docx")) return MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        if (fileName.endsWith(".png")) return MediaType.IMAGE_PNG;
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
