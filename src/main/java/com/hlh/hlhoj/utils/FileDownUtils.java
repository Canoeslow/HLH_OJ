package com.hlh.hlhoj.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;

public class FileDownUtils {

    public  static String generateDownloadUrl(HttpServletRequest request, Long questionId, String filePath) {
        String baseUrl = getBaseUrl(request);
        try {
            // 对文件名进行编码处理
            File file = new File(filePath);
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            return baseUrl + "/teacherQuestion/download/" + questionId + "?fileName=" + encodedFileName;
        } catch (Exception e) {
            // 处理编码异常
            e.printStackTrace();
            return null;
        }
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }

    public static String generateDownloadUrlStudent(HttpServletRequest request, Long questionId, String filePath) {
        String baseUrl = getBaseUrl(request);
        try {
            // 对文件名进行编码处理
            File file = new File(filePath);
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            return baseUrl + "/StudentQuestion/download/" + questionId + "?fileName=" + encodedFileName;
        } catch (Exception e) {
            // 处理编码异常
            e.printStackTrace();
            return null;
        }
    }

    public static String generateDownloadUrlResource(HttpServletRequest request,Long questionId, String filePath) {
        String baseUrl = getBaseUrl(request);
        try {
            // 对文件名进行编码处理
            File file = new File(filePath);
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            return baseUrl + "/TeacherResource/download/" + questionId + "?fileName=" + encodedFileName;
        } catch (Exception e) {
            // 处理编码异常
            e.printStackTrace();
            return null;
        }
    }
}
