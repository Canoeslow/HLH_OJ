package com.hlh.hlhoj.controller;

import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.constant.UserConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.dto.questionsubmit.TQuestionSubmitRequest;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("tsubmit")
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
@Slf4j
public class TeacherSubmitController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private TeacherQuestionService teacherQuestionService;

    @Resource
    private StudentQuestionSubmitService studentQuestionSubmitService;

    @PostMapping("/doTQuestionSubmit")
    public BaseResponse<Long> doTQuestionSubmit(@RequestBody TQuestionSubmitRequest submitRequestion, HttpServletRequest request,@RequestPart("file") MultipartFile file) {
        if(submitRequestion==null || submitRequestion.getQuestionId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()!= UserConstant.DEFAULT_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"只有学生可以做题");
        }
        long resultid = studentQuestionSubmitService.doQuestionSubmit(submitRequestion, loginUser, file);
        return ResultUtils.success(resultid);
    }
}
