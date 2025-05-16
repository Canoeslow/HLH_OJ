package com.hlh.hlhoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.constant.UserConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.exception.ThrowUtils;
import com.hlh.hlhoj.model.dto.questionsubmit.FenshuRequest;
import com.hlh.hlhoj.model.dto.questionsubmit.StudentQueryRequest;
import com.hlh.hlhoj.model.dto.questionsubmit.TQuestionSubmitRequest;
import com.hlh.hlhoj.model.entity.StudentQuestionSubmit;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.StudentQuestionSubmitVO;
import com.hlh.hlhoj.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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

    /**
     * 学生做题
     * @param submitRequestion
     * @param request
     * @param file
     * @return
     */
    @PostMapping("/doTQuestionSubmit")
    public BaseResponse<Long> doTQuestionSubmit(        @RequestParam("questionId") Long questionId,
                                                        @RequestParam("tquestionId") Long tquestionId,
                                                        @RequestParam("classNum") Integer classNum,
                                                        @RequestPart("file") MultipartFile file,
                                                    HttpServletRequest request) {
        if(questionId==null || questionId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(!loginUser.getUserRole().equals(UserConstant.DEFAULT_ROLE)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"只有学生可以做题");
        }
        if(loginUser.getClassNum()==null){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"学生不在对应班级");
        }
        TQuestionSubmitRequest submitRequestion = new TQuestionSubmitRequest();
        submitRequestion.setQuestionId(questionId);
        submitRequestion.setTquestionId(tquestionId);
        submitRequestion.setClassNum(classNum);
        long resultid = studentQuestionSubmitService.doQuestionSubmit(submitRequestion, loginUser, file);
        return ResultUtils.success(resultid);
    }

    /**
     * 老师获得学生的作业
     * @param studentQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/getListSubmit")
    public BaseResponse<Page<StudentQuestionSubmitVO>> getListSubmit(@RequestBody StudentQueryRequest studentQueryRequest, HttpServletRequest request){
        //只有老师和管理员可以查看
        User loginUser = userService.getLoginUser(request);
        long current = studentQueryRequest.getCurrent();
        long size = studentQueryRequest.getPageSize();
        //限制爬虫
        ThrowUtils.throwIf(size>20,ErrorCode.PARAMS_ERROR);
        QueryWrapper<StudentQuestionSubmit> queryWrapper=new QueryWrapper<>();
        if(loginUser.getUserRole().equals(UserConstant.DEFAULT_ROLE)){
            queryWrapper.eq(ObjectUtils.isNotEmpty(loginUser.getClassNum()),"classNum",loginUser.getClassNum());
            queryWrapper.eq(ObjectUtils.isNotEmpty(loginUser.getId()),"Userid",loginUser.getId());
        }
        if(loginUser.getUserRole().equals(UserConstant.TEACHER_ROLE)){
            queryWrapper.eq(ObjectUtils.isNotEmpty(loginUser.getId()),"teacherId",loginUser.getId());
        }
        if(studentQueryRequest!=null){
            Date crrateTime = studentQueryRequest.getCrrateTime();
            queryWrapper.gt(ObjectUtils.isNotEmpty(crrateTime),"crrateTime",crrateTime);
        }
        Page<StudentQuestionSubmit> studentQuestionSubmitPage=studentQuestionSubmitService.page(new Page<>(current,size),queryWrapper);
        return ResultUtils.success(studentQuestionSubmitService.getStudentVOPage(studentQuestionSubmitPage,loginUser,request));
    }

    /**
     * 教师打分
     * @param fenshuRequest
     * @param request
     * @return
     */
    @PostMapping("/fenshu")
    public BaseResponse<Long> updateFenshu(@RequestBody FenshuRequest fenshuRequest, HttpServletRequest request){
        if(fenshuRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()==UserConstant.DEFAULT_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Long id = fenshuRequest.getId();
        StudentQuestionSubmit byId = studentQuestionSubmitService.getById(id);
        if(byId==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if(fenshuRequest.getFenshu()<0||fenshuRequest.getFenshu()>100){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        byId.setFenshu(fenshuRequest.getFenshu());
        boolean b = studentQuestionSubmitService.updateById(byId);
        if(!b){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(id);
    }
}
