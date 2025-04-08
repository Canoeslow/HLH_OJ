package com.hlh.hlhoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 成为教师申请枚举
 */
public enum IsTeacherEnum {
    NOTEACHER("未申请",0),
    BANTEACHER("被拒绝",1),
    TEACHER("通过",2),
    WAIT("申请中",3);

    private final String text;

    private final Integer value;

    IsTeacherEnum(String text,Integer value){
        this.text=text;
        this.value=value;
    }

    /**
     * 获取值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static IsTeacherEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (IsTeacherEnum anEnum : IsTeacherEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
