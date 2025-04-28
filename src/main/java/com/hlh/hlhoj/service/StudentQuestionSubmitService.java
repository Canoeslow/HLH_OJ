package com.hlh.hlhoj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlh.hlhoj.model.dto.questionsubmit.TQuestionSubmitRequest;
import com.hlh.hlhoj.model.entity.StudentQuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.StudentQuestionSubmitVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author ELEX
* @description 针对表【student_question_submit(学生作业提交表)】的数据库操作Service
* @createDate 2025-04-14 10:23:33
*/
public interface StudentQuestionSubmitService extends IService<StudentQuestionSubmit> {

    long doQuestionSubmit(TQuestionSubmitRequest tQuestionSubmitRequest, User loginUser, MultipartFile file);

    Page<StudentQuestionSubmitVO> getStudentVOPage(Page<StudentQuestionSubmit> questionPage, User loginUser, HttpServletRequest request);

}
