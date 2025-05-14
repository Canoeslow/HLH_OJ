package com.hlh.hlhoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.hlh.hlhoj.annotation.AuthCheck;
import com.hlh.hlhoj.common.BaseResponse;
import com.hlh.hlhoj.common.DeleteRequest;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.common.ResultUtils;
import com.hlh.hlhoj.constant.UplodeFileConstant;
import com.hlh.hlhoj.constant.UserConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.exception.ThrowUtils;
import com.hlh.hlhoj.mapper.PostMapper;
import com.hlh.hlhoj.model.dto.question.TeacherQueryRequest;
import com.hlh.hlhoj.model.dto.question.TeacherQuestionRequest;
import com.hlh.hlhoj.model.dto.question.TeacherUpdateRequest;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.QuestionVO;
import com.hlh.hlhoj.model.vo.TquestionText;
import com.hlh.hlhoj.model.vo.TquestionVO;
import com.hlh.hlhoj.service.*;
import com.hlh.hlhoj.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
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
    public BaseResponse<Boolean> addTQuestion(@RequestParam TeacherQuestionRequest teacherQuestionRequest, HttpServletRequest request,@RequestPart("file") MultipartFile file){
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

    /**
     * 教师和管理员分页获取题目列表
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<TquestionVO>> listTQuestionVOByPage(@RequestBody TeacherQueryRequest teacherQueryRequest, HttpServletRequest request){
        if(teacherQueryRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()!=UserConstant.TEACHER_ROLE&&loginUser.getUserRole()!=UserConstant.DEFAULT_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        if(loginUser.getUserRole()==UserConstant.TEACHER_ROLE){
            teacherQueryRequest.setId(loginUser.getId());
        }
        if(loginUser.getUserRole()==UserConstant.DEFAULT_ROLE){
            teacherQueryRequest.setClassNum(loginUser.getClassNum());
        }
        long current = teacherQueryRequest.getCurrent();
        long size = teacherQueryRequest.getPageSize();
        //限制爬虫
        ThrowUtils.throwIf(size>20,ErrorCode.PARAMS_ERROR);
        Page<TeacherQuestion> teacherQuestionPage=teacherQuestionService.page(new Page<>(current,size),teacherQuestionService.getQueryWrapper(teacherQueryRequest));
        return ResultUtils.success(teacherQuestionService.getTeacherQuestionVOPage(teacherQuestionPage,loginUser));
    }

    /**
     * 管理员获取题目信息
     * @param teacherQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<Page<TquestionVO>> listTQuestionAdmin(@RequestBody TeacherQueryRequest teacherQueryRequest,HttpServletRequest request){
        if(teacherQueryRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        if(loginUser.getUserRole()!=UserConstant.ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        long current = teacherQueryRequest.getCurrent();
        long size = teacherQueryRequest.getPageSize();
        //限制爬虫
        ThrowUtils.throwIf(size>20,ErrorCode.PARAMS_ERROR);
        Page<TeacherQuestion> teacherQuestionPage = teacherQuestionService.page(new Page<>(current, size),teacherQuestionService.getQueryWrapper(teacherQueryRequest));
        return ResultUtils.success(teacherQuestionService.getTeacherQuestionVOPage(teacherQuestionPage,loginUser));
    }

    /**
     * 删除
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("delete")
    public BaseResponse<Boolean> deleteTQuetion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request){
        if(deleteRequest==null|| deleteRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Long id = deleteRequest.getId();
        //判断是否存在
        TeacherQuestion oldQuestion = teacherQuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestion==null,ErrorCode.NOT_FOUND_ERROR);
        //仅本人或管理员可以删除
        if(!oldQuestion.getTeacherId().equals(loginUser.getId())||loginUser.getUserRole()!=UserConstant.ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = teacherQuestionService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员和本人进行修改）
     * @param teacherUpdateRequest
     * @param request
     * @param file
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateTquestion(@RequestParam TeacherUpdateRequest teacherUpdateRequest, HttpServletRequest request,@RequestPart("file") MultipartFile file){
        if(teacherUpdateRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Long id = teacherUpdateRequest.getId();
        TeacherQuestion oldQuestion = teacherQuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestion==null,ErrorCode.NOT_FOUND_ERROR);
        if(loginUser.getId()!=oldQuestion.getTeacherId()||loginUser.getUserRole()!=UserConstant.ADMIN_ROLE){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        String filename=new String();
        if (!file.isEmpty()) {
            filename = FileUtils.UpdateFile(file, loginUser, UplodeFileConstant.TEACHER_CLASS,oldQuestion.getTextPath());
        }
        TeacherQuestion teacherQuestion = new TeacherQuestion();
        BeanUtils.copyProperties(teacherUpdateRequest,teacherQuestion);
        if(filename!=null){
            teacherQuestion.setTextPath(filename);
        }
        boolean result = teacherQuestionService.updateById(teacherQuestion);
        return ResultUtils.success(result);
    }

    /**
     * 根据id获取题目信息
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/get/VO")
    public BaseResponse<TquestionText> getTQuestionVOById(long id, HttpServletRequest request){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TeacherQuestion tquestion = teacherQuestionService.getById(id);
        if(tquestion==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(teacherQuestionService.getTeacherQuestionVO(tquestion,request));
    }


}
