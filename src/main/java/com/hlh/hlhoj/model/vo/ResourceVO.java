package com.hlh.hlhoj.model.vo;

import com.hlh.hlhoj.model.entity.Resource;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResourceVO implements Serializable {
    private Long id;

    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * 文档路径
     */
    private String textpath;

    /**
     * 创建时间
     */
    private Date crratetime;

    /**
     * 资源标题
     */
    private String title;
    /**
     * 用户名
     */
    private String userName;
    private static final long serialVersionUID = 1L;

    public static Resource voToObj(ResourceVO tquestionVO) {
        if (tquestionVO == null) {
            return null;
        }
        Resource tquestion = new Resource();
        BeanUtils.copyProperties(tquestionVO, tquestion);
        return tquestion;
    }
    /**
     * 对象转包装类
     *
     * @param tquestion
     * @return
     */
    public static ResourceVO objToVo(Resource tquestion) {
        if (tquestion == null) {
            return null;
        }
        ResourceVO TquestionVO = new ResourceVO();
        BeanUtils.copyProperties(tquestion, TquestionVO);
        return TquestionVO;
    }
}
