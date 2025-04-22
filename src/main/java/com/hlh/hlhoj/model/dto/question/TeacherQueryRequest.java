package com.hlh.hlhoj.model.dto.question;

import com.hlh.hlhoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherQueryRequest extends PageRequest implements Serializable {
    /**
     * 教师ID
     */
    private Long id;

    /**
     * 班级ID
     */
    private Integer classNum;

    /**
     * 开始时间
     */
    private Date createTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private static final long serialVersionUID = 1L;
}
