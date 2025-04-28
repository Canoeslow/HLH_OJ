package com.hlh.hlhoj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CreateFeedBackRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 问题
     */
    private String questiontext;

    /**
     * 答案
     */
    private String feedbacktext;

    /**
     * 创建时间
     */
    private Date crratetime;
    private static final long serialVersionUID = 1L;
}
