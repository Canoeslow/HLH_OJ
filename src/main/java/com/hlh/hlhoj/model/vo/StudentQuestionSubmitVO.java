package com.hlh.hlhoj.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hlh.hlhoj.model.entity.StudentQuestionSubmit;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class StudentQuestionSubmitVO implements Serializable {
    /**
     * 提价id
     */
    private Long id;

    /**
     * 文档路径
     */
    private String textPath;

    /**
     * code题目id
     */
    private Long questionId;

    /**
     * 教师题目id
     */
    private Long tquestionId;


    /**
     * 用户提交ID
     */
    private Long Userid;

    /**
     * 班级
     */
    private Integer classNum;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 学生文件提交
     */
    private String Studentfileurl;

    /**
     * 学生分数
     */
    private Integer fenshu;

    public static StudentQuestionSubmit voToObj(StudentQuestionSubmitVO tquestionVO) {
        if (tquestionVO == null) {
            return null;
        }
        StudentQuestionSubmit tquestion = new StudentQuestionSubmit();
        BeanUtils.copyProperties(tquestionVO, tquestion);
        return tquestion;
    }
    /**
     * 对象转包装类
     *
     * @param tquestion
     * @return
     */
    public static StudentQuestionSubmitVO objToVo(StudentQuestionSubmit tquestion) {
        if (tquestion == null) {
            return null;
        }
        StudentQuestionSubmitVO TquestionVO = new StudentQuestionSubmitVO();
        BeanUtils.copyProperties(tquestion, TquestionVO);
        return TquestionVO;
    }
    private static final long serialVersionUID = 1L;
}
