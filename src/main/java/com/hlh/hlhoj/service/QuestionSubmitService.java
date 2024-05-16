package com.hlh.hlhoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlh.hlhoj.model.dto.question.QuestionQueryRequest;
import com.hlh.hlhoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.hlh.hlhoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.hlh.hlhoj.model.entity.Question;
import com.hlh.hlhoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.QuestionSubmitVO;
import com.hlh.hlhoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交操作
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
