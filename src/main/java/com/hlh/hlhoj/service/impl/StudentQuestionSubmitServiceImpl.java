package com.hlh.hlhoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hlh.hlhoj.common.ErrorCode;
import com.hlh.hlhoj.constant.UplodeFileConstant;
import com.hlh.hlhoj.exception.BusinessException;
import com.hlh.hlhoj.model.dto.questionsubmit.TQuestionSubmitRequest;
import com.hlh.hlhoj.model.entity.QuestionSubmit;
import com.hlh.hlhoj.model.entity.StudentQuestionSubmit;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.StudentQuestionSubmitVO;
import com.hlh.hlhoj.service.QuestionService;
import com.hlh.hlhoj.service.QuestionSubmitService;
import com.hlh.hlhoj.service.StudentQuestionSubmitService;
import com.hlh.hlhoj.mapper.StudentQuestionSubmitMapper;
import com.hlh.hlhoj.service.TeacherQuestionService;
import com.hlh.hlhoj.utils.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private QuestionService questionService;
    @Resource
    private StudentQuestionSubmitService studentQuestionSubmitService;
    @Override
    public long doQuestionSubmit(TQuestionSubmitRequest tQuestionSubmitRequest, User loginUser, MultipartFile file) {
        if(tQuestionSubmitRequest.getTquestionId()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TeacherQuestion question = teacherQuestionService.getById(tQuestionSubmitRequest.getTquestionId());
        if(question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long questionid = question.getQuestionid();
        if(questionid!=null && tQuestionSubmitRequest.getQuestionId()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<QuestionSubmit> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(loginUser.getId()),"userId",loginUser.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionid),"questionId",questionid);
        queryWrapper.orderByDesc("createTime");
        queryWrapper.last("limit 1");
        QuestionSubmit UserSubmitLog = questionSubmitService.getOne(queryWrapper);
        //获取当前时间
        Date now = new Date();
        Date endTime = question.getEndTime();
        Date createTime = question.getCreateTime();
        if(endTime.before(now) || createTime.after(now)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(file.isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //处理上传的文件
        String filename = FileUtils.UplodeFileStudent(file, loginUser, UplodeFileConstant.STUDENT_COMMINT,questionid);
        StudentQuestionSubmit studentQuestionSubmit = new StudentQuestionSubmit();
        BeanUtils.copyProperties(tQuestionSubmitRequest,studentQuestionSubmit);
        studentQuestionSubmit.setClassNum(loginUser.getClassNum());
        studentQuestionSubmit.setUserid(loginUser.getId());
        studentQuestionSubmit.setTextPath(filename);
        studentQuestionSubmit.setCrrateTime(new Date());
        studentQuestionSubmit.setUpdateTime(new Date());
        studentQuestionSubmit.setQuestionId(UserSubmitLog.getId());
        studentQuestionSubmit.setUserName(loginUser.getUserName());
        QueryWrapper<StudentQuestionSubmit> queryWrapperStudent=new QueryWrapper<>();
        queryWrapperStudent.eq(ObjectUtils.isNotEmpty(loginUser.getId()),"userId",loginUser.getId());
        queryWrapperStudent.eq(ObjectUtils.isNotEmpty(tQuestionSubmitRequest.getTquestionId()),"tquestionId",tQuestionSubmitRequest.getTquestionId());
        StudentQuestionSubmit one = studentQuestionSubmitService.getOne(queryWrapperStudent);
        boolean save=false;
        Long index;
        if(one!=null){
            studentQuestionSubmit.setId(one.getId());
            save = this.updateById(studentQuestionSubmit);
            index=studentQuestionSubmit.getId();
        }else{
            save = this.save(studentQuestionSubmit);
            index = studentQuestionSubmit.getId();
        }
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return index;
    }

    @Override
    public Page<StudentQuestionSubmitVO> getStudentVOPage(Page<StudentQuestionSubmit> questionPage, User loginUser, HttpServletRequest request) {
        List<StudentQuestionSubmit> records = questionPage.getRecords();
        Page<StudentQuestionSubmitVO> studentVo = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if(CollectionUtils.isEmpty(records)){
            return studentVo;
        }
        List<StudentQuestionSubmitVO> studentVolist = records.stream().map(StudentQuestionSubmit -> {
            StudentQuestionSubmitVO studentQuestionSubmitVO = StudentQuestionSubmitVO.objToVo(StudentQuestionSubmit);
            if(StudentQuestionSubmit.getTextPath()!=null){
                String downloadurl = TeacherQuestionServiceImpl.generateDownloadUrlStudent(request, StudentQuestionSubmit.getId(), StudentQuestionSubmit.getTextPath());
                studentQuestionSubmitVO.setStudentfileurl(downloadurl);
            }
            return studentQuestionSubmitVO;
        }).collect(Collectors.toList());
        studentVo.setRecords(studentVolist);
        return studentVo;
    }
}




