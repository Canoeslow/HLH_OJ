package com.hlh.hlhoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户论坛
 * @TableName whchat
 */
@TableName(value ="whchat")
public class Whchat implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * 内容
     */
    private String textvalue;

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
     * 创建用户 id
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * 创建用户 id
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * 内容
     */
    public String getTextvalue() {
        return textvalue;
    }

    /**
     * 内容
     */
    public void setTextvalue(String textvalue) {
        this.textvalue = textvalue;
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
        Whchat other = (Whchat) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getTextvalue() == null ? other.getTextvalue() == null : this.getTextvalue().equals(other.getTextvalue()))
            && (this.getCrratetime() == null ? other.getCrratetime() == null : this.getCrratetime().equals(other.getCrratetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getTextvalue() == null) ? 0 : getTextvalue().hashCode());
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
        sb.append(", userid=").append(userid);
        sb.append(", textvalue=").append(textvalue);
        sb.append(", crratetime=").append(crratetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}