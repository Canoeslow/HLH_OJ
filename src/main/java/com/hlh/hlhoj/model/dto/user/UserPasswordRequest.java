package com.hlh.hlhoj.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码
 */
@Data
public class UserPasswordRequest implements Serializable {

    /**
     * 旧密码
     */
    private String oldUserPassword;
    /**
     * 再次确认密码
     */
    private String checkPassword;
    /**
     * 修改的密码
     */
    private String userPassword;

    private static final long serialVersionUID = 1L;
}
