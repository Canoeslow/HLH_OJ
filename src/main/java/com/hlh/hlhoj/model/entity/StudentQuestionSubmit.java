package com.hlh.hlhoj.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学生作业提交表
 * @TableName student_question_submit
 */
@TableName(value ="student_question_submit")
@Data
public class StudentQuestionSubmit implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 创建时间
     */
    private Date crrateTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}