package com.hlh.hlhoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hlh.hlhoj.model.dto.questionsubmit.TQuestionSubmitRequest;
import com.hlh.hlhoj.model.entity.StudentQuestionSubmit;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.service.QuestionSubmitService;
import com.hlh.hlhoj.service.StudentQuestionSubmitService;
import com.hlh.hlhoj.mapper.StudentQuestionSubmitMapper;
import com.hlh.hlhoj.service.TeacherQuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author ELEX
* @description 针对表【student_question_submit(学生作业提交表)】的数据库操作Service实现
* @createDate 2025-04-14 10:23:33
*/
@Service
public class StudentQuestionSubmitServiceImpl extends ServiceImpl<StudentQuestionSubmitMapper, StudentQuestionSubmit>
    implements StudentQuestionSubmitService{

    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private TeacherQuestionService teacherQuestionService;
    @Override
    public long doQuestionSubmit(TQuestionSubmitRequest tQuestionSubmitRequest, User loginUser) {
        if(tQuestionSubmitRequest.getQuestionId()==null||tQuestionSubmitRequest.get)
    }
}




