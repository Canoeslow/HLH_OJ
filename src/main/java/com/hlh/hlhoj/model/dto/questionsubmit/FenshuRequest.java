package com.hlh.hlhoj.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;
@Data
public class FenshuRequest implements Serializable {

    /**
     * 题目id
     */
    private Long id;

    /**
     * 分数
     */
    private Integer fenshu;
    private static final long serialVersionUID = 1L;
}
