package com.hlh.hlhoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户答疑
 * @TableName userfeedback
 */
@TableName(value ="userfeedback")
public class Userfeedback implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 问题
     */
    private String questiontext;

    /**
     * 答案
     */
    private String feedbacktext;

    /**
     * 创建时间
     */
    private Date crratetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 问题
     */
    public String getQuestiontext() {
        return questiontext;
    }

    /**
     * 问题
     */
    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    /**
     * 答案
     */
    public String getFeedbacktext() {
        return feedbacktext;
    }

    /**
     * 答案
     */
    public void setFeedbacktext(String feedbacktext) {
        this.feedbacktext = feedbacktext;
    }

    /**
     * 创建时间
     */
    public Date getCrratetime() {
        return crratetime;
    }

    /**
     * 创建时间
     */
    public void setCrratetime(Date crratetime) {
        this.crratetime = crratetime;
    }

    /**
     * 是否删除
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 是否删除
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Userfeedback other = (Userfeedback) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getQuestiontext() == null ? other.getQuestiontext() == null : this.getQuestiontext().equals(other.getQuestiontext()))
            && (this.getFeedbacktext() == null ? other.getFeedbacktext() == null : this.getFeedbacktext().equals(other.getFeedbacktext()))
            && (this.getCrratetime() == null ? other.getCrratetime() == null : this.getCrratetime().equals(other.getCrratetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQuestiontext() == null) ? 0 : getQuestiontext().hashCode());
        result = prime * result + ((getFeedbacktext() == null) ? 0 : getFeedbacktext().hashCode());
        result = prime * result + ((getCrratetime() == null) ? 0 : getCrratetime().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", questiontext=").append(questiontext);
        sb.append(", feedbacktext=").append(feedbacktext);
        sb.append(", crratetime=").append(crratetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}