package com.hlh.hlhoj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WhchatRequest implements Serializable {
    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * 内容
     */
    private String textvalue;

    /**
     * 创建时间
     */
    private Date crratetime;

    private static final long serialVersionUID = 1L;
}
