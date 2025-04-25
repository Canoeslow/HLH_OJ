package com.hlh.hlhoj.model.vo;

import com.hlh.hlhoj.model.entity.TeacherQuestion;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
public class TquestionText implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 班级号
     */
    private Integer classNum;

    /**
     * 题目id
     */
    private Long questionid;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户提交id
     */
    private Long userStudentSubmitId;

    /**
     * 教师文件题目文件
     */
    private String Teacherfileurl;

    /**
     * 学生文件提交
     */
    private String Studentfileurl;

    /**
     *
     * @param tquestionVO
     * @return
     */


    public static TeacherQuestion voToObj(TquestionText tquestionVO) {
        if (tquestionVO == null) {
            return null;
        }
        TeacherQuestion tquestion = new TeacherQuestion();
        BeanUtils.copyProperties(tquestionVO, tquestion);
        return tquestion;
    }
    /**
     * 对象转包装类
     *
     * @param tquestion
     * @return
     */
    public static TquestionText objToVo(TeacherQuestion tquestion) {
        if (tquestion == null) {
            return null;
        }
        TquestionText TquestionVO = new TquestionText();
        BeanUtils.copyProperties(tquestion, TquestionVO);
        return TquestionVO;
    }
    private static final long serialVersionUID = 1L;
}
