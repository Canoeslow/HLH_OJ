package com.hlh.hlhoj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hlh.hlhoj.judge.codesandbox.model.JudgeInfo;
import com.hlh.hlhoj.model.entity.QuestionSubmit;
import com.hlh.hlhoj.model.entity.TeacherQuestion;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

public class TquestionVO implements Serializable {
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

    public static TeacherQuestion voToObj(TquestionVO tquestionVO) {
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
    public static TquestionVO objToVo(TeacherQuestion tquestion) {
        if (tquestion == null) {
            return null;
        }
        TquestionVO TquestionVO = new TquestionVO();
        BeanUtils.copyProperties(tquestion, TquestionVO);
        return questionSubmitVO;
    }
}
