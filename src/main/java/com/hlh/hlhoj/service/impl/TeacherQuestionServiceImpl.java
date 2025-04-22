package com.hlh.hlhoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hlh.hlhoj.model.dto.question.TeacherQueryRequest;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import com.hlh.hlhoj.model.entity.User;
import com.hlh.hlhoj.model.vo.TquestionVO;
import com.hlh.hlhoj.service.TeacherQuestionService;
import com.hlh.hlhoj.mapper.TeacherQuestionMapper;
import com.hlh.hlhoj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public TquestionVO getTeacherQuestionVO(TeacherQuestion tquestion, HttpServletRequest request) {
        TquestionVO tquestionVO = TquestionVO.objToVo(tquestion);
        Long teacherId = tquestion.getTeacherId();
        User teacher = userService.getById(teacherId);
        tquestionVO.setUserName(teacher.getUserName());
        return tquestionVO;
    }
}




