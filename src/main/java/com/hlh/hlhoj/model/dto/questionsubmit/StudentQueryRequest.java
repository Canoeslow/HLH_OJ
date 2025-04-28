package com.hlh.hlhoj.model.dto.questionsubmit;

import com.hlh.hlhoj.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StudentQueryRequest extends PageRequest implements Serializable {

    /**
     * 题目创建时间
     */
    private Date crrateTime;

    private static final long serialVersionUID = 1L;
}
