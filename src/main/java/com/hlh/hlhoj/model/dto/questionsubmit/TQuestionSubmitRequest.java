package com.hlh.hlhoj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TQuestionSubmitRequest implements Serializable {
    /**
     * 题目 id
     */
    private Long id;
    /**
     * code题目id
     */
    private Long questionId;

    /**
     * 教师题目id
     */
    private Long tquestionId;

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
     * 用户提交ID
     */
    private Long Userid;

    private static final long serialVersionUID = 1L;
}
