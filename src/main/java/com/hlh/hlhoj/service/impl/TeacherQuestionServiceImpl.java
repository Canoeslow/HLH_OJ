package com.hlh.hlhoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hlh.hlhoj.model.dto.question.TeacherQueryRequest;
import com.hlh.hlhoj.model.entity.QuestionSubmit;
import com.hlh.hlhoj.model.entity.StudentQuestionSubmit;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.TquestionText;
import com.hlh.hlhoj.model.vo.TquestionVO;
import com.hlh.hlhoj.service.QuestionSubmitService;
import com.hlh.hlhoj.service.StudentQuestionSubmitService;
import com.hlh.hlhoj.service.TeacherQuestionService;
import com.hlh.hlhoj.mapper.TeacherQuestionMapper;
import com.hlh.hlhoj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author ELEX
* @description 针对表【teacher_question(教师题目创建)】的数据库操作Service实现
* @createDate 2025-04-14 10:22:52
*/
@Service
public class TeacherQuestionServiceImpl extends ServiceImpl<TeacherQuestionMapper, TeacherQuestion>
    implements TeacherQuestionService{

    @Resource
    private UserService userService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private StudentQuestionSubmitService studentQuestionSubmitService;
    @Override
    public QueryWrapper<TeacherQuestion> getQueryWrapper(TeacherQueryRequest teacherQueryRequest) {
        QueryWrapper<TeacherQuestion> queryWrapper=new QueryWrapper<>();
        if(teacherQueryRequest==null){
            return queryWrapper;
        }
        Long id = teacherQueryRequest.getId();
        Integer classNum = teacherQueryRequest.getClassNum();
        Date createTime = teacherQueryRequest.getCreateTime();
        Date endTime = teacherQueryRequest.getEndTime();
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),"teacherId",id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(classNum),"classNum",classNum);
        queryWrapper.eq("isDelete",false);
        if(createTime!=null&&endTime!=null){
            queryWrapper.between("createTime",createTime,endTime);
        }
        return queryWrapper;
    }

    @Override
    public Page<TquestionVO> getTeacherQuestionVOPage(Page<TeacherQuestion> questionPage, User loginUser) {
        List<TeacherQuestion> questionList = questionPage.getRecords();
        Page<TquestionVO> tquestionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if(CollectionUtils.isEmpty(questionList)){
            return tquestionVOPage;
        }
        List<TquestionVO> tquestionVOList = questionList.stream().map(TeacherQuestion -> {
            TquestionVO tquestionVO = TquestionVO.objToVo(TeacherQuestion);
            tquestionVO.setUserName(loginUser.getUserName());
            return tquestionVO;
        }).collect(Collectors.toList());
        tquestionVOPage.setRecords(tquestionVOList);
        return tquestionVOPage;
    }

    @Override
    public TquestionText getTeacherQuestionVO(TeacherQuestion tquestion, HttpServletRequest request) {
        TquestionText tquestionVO = TquestionText.objToVo(tquestion);
        User loginUser = userService.getLoginUser(request);
        QueryWrapper<QuestionSubmit> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(loginUser.getId()),"userId",loginUser.getId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(tquestion.getId()),"questionId",tquestion.getId());
        queryWrapper.orderByDesc("createTime");
        queryWrapper.last("limit 1");
        QuestionSubmit UserSubmitLog = questionSubmitService.getOne(queryWrapper);
        QueryWrapper<StudentQuestionSubmit> queryWrapperStudent=new QueryWrapper<>();
        queryWrapperStudent.eq(ObjectUtils.isNotEmpty(loginUser.getId()),"userId",loginUser.getId());
        queryWrapperStudent.eq(ObjectUtils.isNotEmpty(tquestion.getId()),"tquestionId",tquestion.getId());
        StudentQuestionSubmit one = studentQuestionSubmitService.getOne(queryWrapperStudent);
        String Studentfilename=one!=null?one.getTextPath():null;
        String teacherfilename = tquestion.getTextPath();
        // 生成教师文件下载链接
        String teacherFileDownloadUrl = generateDownloadUrl(request, tquestion.getId(), teacherfilename);
        tquestionVO.setTeacherfileurl(teacherFileDownloadUrl);
        // 生成学生文件下载链接
        if (Studentfilename != null) {
            String studentFileDownloadUrl = generateDownloadUrlStudent(request, one.getId(), Studentfilename);
            tquestionVO.setStudentfileurl(studentFileDownloadUrl);
        }
        Long teacherId = tquestion.getTeacherId();
        User teacher = userService.getById(teacherId);
        tquestionVO.setUserName(teacher.getUserName());
        tquestionVO.setUserStudentSubmitId(UserSubmitLog!=null?UserSubmitLog.getId():null);
        return tquestionVO;
    }

    private String generateDownloadUrl(HttpServletRequest request, Long questionId, String filePath) {
        String baseUrl = getBaseUrl(request);
        try {
            // 对文件名进行编码处理
            File file = new File(filePath);
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            return baseUrl + "/teacherQuestion/download/" + questionId + "?fileName=" + encodedFileName;
        } catch (Exception e) {
            // 处理编码异常
            e.printStackTrace();
            return null;
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + "://" + serverName + ":" + serverPort + contextPath;
    }

    private String generateDownloadUrlStudent(HttpServletRequest request, Long questionId, String filePath) {
        String baseUrl = getBaseUrl(request);
        try {
            // 对文件名进行编码处理
            File file = new File(filePath);
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            return baseUrl + "/StudentQuestion/download/" + questionId + "?fileName=" + encodedFileName;
        } catch (Exception e) {
            // 处理编码异常
            e.printStackTrace();
            return null;
        }
    }
}




