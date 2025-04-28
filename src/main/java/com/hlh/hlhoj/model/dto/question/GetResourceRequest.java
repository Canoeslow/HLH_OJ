package com.hlh.hlhoj.model.dto.question;

import com.hlh.hlhoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetResourceRequest extends PageRequest implements Serializable {
    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * 创建时间
     */
    private Date crratetime;

    private static final long serialVersionUID = 1L;
}
