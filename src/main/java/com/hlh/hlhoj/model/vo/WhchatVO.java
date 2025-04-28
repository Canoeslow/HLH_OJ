package com.hlh.hlhoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WhchatVO implements Serializable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 内容
     */
    private String textvalue;

    /**
     * 创建时间
     */
    private Date crratetime;

    /**
     * 用户昵称
     */
    private String userName;

    private static final long serialVersionUID = 1L;
}
