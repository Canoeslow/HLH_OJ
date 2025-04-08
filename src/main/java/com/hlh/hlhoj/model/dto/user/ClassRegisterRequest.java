package com.hlh.hlhoj.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClassRegisterRequest implements Serializable {
    /**
     * 班级
     */
    private Integer classNum;

    private static final long serialVersionUID = 1L;
}
