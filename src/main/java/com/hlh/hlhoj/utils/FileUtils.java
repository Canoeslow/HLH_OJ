package com.hlh.hlhoj.utils;

import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.constant.UplodeFileConstant;
import com.hlh.hlhoj.constant.UserConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

    //更新老师上传的实验内容
    public static String UpdateFile(MultipartFile file,User loginUser,String Constant,String oldfilename){
        String originalFilename = file.getOriginalFilename();
        if(originalFilename!=null){
            if(originalFilename.endsWith(".docx") || originalFilename.endsWith(".doc") || originalFilename.endsWith(".pdf")){
                int lastIndexDot = originalFilename.lastIndexOf(".");
                String fileExtension = lastIndexDot != -1? originalFilename.substring(lastIndexDot) : "";
                String saveDirectory="D/FileCache";
                if(loginUser.getUserRole()== UserConstant.TEACHER_ROLE||loginUser.getUserRole()==UserConstant.ADMIN_ROLE){
                    saveDirectory="D/FileCache/teacherQuestion";
                }
                if(loginUser.getUserRole()==UserConstant.DEFAULT_ROLE){
                    saveDirectory="D/FileCache/studentSubmit";
                }
                File directory = new File(saveDirectory);
                if(!directory.exists()){directory.mkdirs();}
                int last = oldfilename.lastIndexOf(".");
                String fileNameWithoutExtension = last != -1? oldfilename.substring(0, last) : oldfilename;
                String newfilename=fileNameWithoutExtension+fileExtension;
                File file1 = new File(directory, newfilename);
                try{
                    file.transferTo(file1);
                }catch (IOException e){
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件保存失败");
                }
                return newfilename;
            }else{
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的文件格式，仅支持 Word 和 PDF 文件");
            }
        }
        return null;
    }
    //处理老师上传实验内容
    public static String UplodeFile(MultipartFile file, User loginUser,String Constant){
        // 获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            if(Constant== UplodeFileConstant.TEACHER_CLASS){
                if (originalFilename.endsWith(".docx") || originalFilename.endsWith(".doc") ||
                        originalFilename.endsWith(".pdf")) {
                    String uuid = RandomStringUtils.randomAlphanumeric(8);
                    int lastIndexOfDot = originalFilename.lastIndexOf('.');
                    String fileExtension = lastIndexOfDot != -1? originalFilename.substring(lastIndexOfDot) : "";
                    // 判断文件格式是否为 Word 或 PDF
                    String saveDirectory="D/FileCache"; // 请替换为实际的保存目录
                    saveDirectory="D/FileCache/teacherQuestion";
                    File directory=new File(saveDirectory);
                    if(!directory.exists()) {directory.mkdirs();}
                    String newFilename=uuid.toString()+loginUser.getUserRole()+fileExtension;
                    File destFile =new File(directory,newFilename);
                    try{
                        file.transferTo(destFile);
                    }catch (IOException e){
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件保存失败");
                    }
                    return newFilename;
                } else {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的文件格式，仅支持 Word 和 PDF 文件");
                }
            }
        }
        return null;
    }
    //处理学生上传作业文件
    public static String UplodeFileStudent(MultipartFile file,User loginUser,String Constant,Long questionId){
        // 获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            if(Constant== UplodeFileConstant.STUDENT_COMMINT){
                if (originalFilename.endsWith(".docx") || originalFilename.endsWith(".doc") ||
                        originalFilename.endsWith(".pdf")) {
                    int lastIndexOfDot = originalFilename.lastIndexOf('.');
                    String fileExtension = lastIndexOfDot != -1? originalFilename.substring(lastIndexOfDot) : "";
                    // 判断文件格式是否为 Word 或 PDF
                    String saveDirectory="D/FileCache/studentSubmit";
                    File directory=new File(saveDirectory);
                    if(!directory.exists()) {directory.mkdirs();}
                    String newFilename=questionId+"_"+loginUser.getClassNum()+"_"+loginUser.getId()+fileExtension;
                    File destFile =new File(directory,newFilename);
                    try{
                        file.transferTo(destFile);
                    }catch (IOException e){
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件保存失败");
                    }
                    return newFilename;
                } else {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的文件格式，仅支持 Word 和 PDF 文件");
                }
            }
        }
        return null;
    }

    //教师上传资源
    public static String UplodeResource(MultipartFile file,User loginUser){
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
                if (originalFilename.endsWith(".docx") || originalFilename.endsWith(".doc") ||
                        originalFilename.endsWith(".pdf")) {
                    int lastIndexOfDot = originalFilename.lastIndexOf('.');
                    String fileExtension = lastIndexOfDot != -1? originalFilename.substring(lastIndexOfDot) : "";
                    // 判断文件格式是否为 Word 或 PDF
                    String saveDirectory="D/FileCache/Resource";
                    File directory=new File(saveDirectory);
                    if(!directory.exists()) {directory.mkdirs();}
                    String newFilename=UUID.randomUUID().toString() +"_"+loginUser.getId()+fileExtension;
                    File destFile =new File(directory,newFilename);
                    try{
                        file.transferTo(destFile);
                    }catch (IOException e){
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件保存失败");
                    }
                    return newFilename;
                } else {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的文件格式，仅支持 Word 和 PDF 文件");
                }
        }
        return null;
    }
}
