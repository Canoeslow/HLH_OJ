package com.hlh.hlhoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlh.hlhoj.model.dto.question.TeacherQueryRequest;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.TquestionText;
import com.hlh.hlhoj.model.vo.TquestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author ELEX
* @description 针对表【teacher_question(教师题目创建)】的数据库操作Service
* @createDate 2025-04-14 10:22:52
*/
public interface TeacherQuestionService extends IService<TeacherQuestion> {
    /**
     * 教师学生获取对应的题目信息
     * @param teacherQueryRequest
     * @return
     */
    QueryWrapper<TeacherQuestion> getQueryWrapper(TeacherQueryRequest teacherQueryRequest);

    Page<TquestionVO> getTeacherQuestionVOPage(Page<TeacherQuestion> questionPage, User loginUser);

    TquestionText getTeacherQuestionVO(TeacherQuestion tquestion, HttpServletRequest request);
}
