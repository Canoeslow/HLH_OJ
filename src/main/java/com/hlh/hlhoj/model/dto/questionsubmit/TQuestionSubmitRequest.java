package com.hlh.hlhoj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TQuestionSubmitRequest implements Serializable {
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

    private static final long serialVersionUID = 1L;
}
