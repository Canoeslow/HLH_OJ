package com.hlh.hlhoj.controller;

import cn.hutool.db.Page;
import com.google.gson.Gson;
import com.hlh.hlhoj.annotation.AuthCheck;
import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.constant.UplodeFileConstant;
import com.hlh.hlhoj.constant.UserConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.exception.ThrowUtils;
import com.hlh.hlhoj.mapper.PostMapper;
import com.hlh.hlhoj.model.dto.question.TeacherQuestionRequest;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.service.*;
import com.hlh.hlhoj.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tquestion")
@CrossOrigin(origins = "134.175.223.105:6630", allowCredentials = "true")
@Slf4j
public class TeacherController {
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

    private final static Gson TGSON =new Gson();

    /**
     * 创建实验任务
     * @param teacherQuestionRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.TEACHER_ROLE)
    public BaseResponse<Boolean> addTQuestion(@RequestBody TeacherQuestionRequest teacherQuestionRequest, HttpServletRequest request,@RequestPart("file") MultipartFile file){
        if(teacherQuestionRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        String filename=new String();
        //创建题目的时候附带题目处理文件上传
        if (!file.isEmpty()) {
            filename = FileUtils.UplodeFile(file, loginUser, UplodeFileConstant.TEACHER_CLASS);
        }
            String[] ignore={"classNum"};
            List<TeacherQuestion> teacherQuestions=new ArrayList<>();
            List<Integer> classNum = teacherQuestionRequest.getClassNum();
            for(Integer classnum :classNum){
                TeacherQuestion teacherQuestion =new TeacherQuestion();
                BeanUtils.copyProperties(teacherQuestionRequest,teacherQuestion,ignore);
                teacherQuestion.setTeacherId(loginUser.getId());
                teacherQuestion.setClassNum(classnum);
                teacherQuestion.setTextPath(filename);
                teacherQuestions.add(teacherQuestion);
            }
        boolean result = teacherQuestionService.saveBatch(teacherQuestions);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    public BaseResponse<Page<TquestionVO>> listTQuestionVOByPage(@RequestBody)
}
