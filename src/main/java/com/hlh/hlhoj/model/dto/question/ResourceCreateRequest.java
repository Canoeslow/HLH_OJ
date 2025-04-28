package com.hlh.hlhoj.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResourceCreateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 资源标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date crratetime;

    private static final long serialVersionUID = 1L;
}
