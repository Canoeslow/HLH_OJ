package com.hlh.hlhoj.controller;

import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.service.ResourceService;
import com.hlh.hlhoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("ResourceFiledown")
@Slf4j
public class ResourceFiledownController {
    @Resource
    private UserService userService;
    @Resource
    private ResourceService resourceService;

    @GetMapping("/download/{resourceId}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable Long resourceId, @RequestParam String fileName, HttpServletResponse response){
        com.hlh.hlhoj.model.entity.Resource resource = resourceService.getById(resourceId);
        if (resource == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 构建文件路径
        String teacherBase = "D:\\\\FileCache\\\\Resource\\\\";
        String textPath = teacherBase + resource.getTextpath();
        File file = new File(textPath);
        // 检查文件是否存在
        if (!file.exists()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 记录文件读取前信息
        log.info("文件读取前，文件长度: {}", file.length());
        // 创建文件资源
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        // 构建HTTP头
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置Content-Disposition头，使用UTF-8编码文件名
        try {
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"");
        } catch (UnsupportedEncodingException e) {
            // 通常不会发生，因为UTF-8是标准编码
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        }
        // 确定文件类型对应的MIME类型
        MediaType mediaType;
        if (fileName.endsWith(".doc")) {
            mediaType = MediaType.valueOf("application/msword");
        } else if (fileName.endsWith(".docx")) {
            mediaType = MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } else if (fileName.endsWith(".pdf")) {
            mediaType = MediaType.APPLICATION_PDF;
        } else {
            // 默认使用二进制流类型
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        // 设置Content-Type
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, mediaType.toString());
        // 暴露必要的响应头给前端
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION + "," + HttpHeaders.CONTENT_TYPE);
        // 设置缓存控制
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        // 验证文件类型
        if (!fileName.endsWith(".doc") && !fileName.endsWith(".docx") && !fileName.endsWith(".pdf")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的文件类型");
        }
        // 记录文件读取后信息
        log.info("文件读取后，文件资源信息: {}", fileSystemResource);
        // 返回响应实体
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(fileSystemResource);
    }
}
