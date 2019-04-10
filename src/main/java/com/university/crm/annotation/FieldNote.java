package com.university.crm.annotation;

import com.university.crm.enums.FieldEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldNote {
    /**
     * 名称
     */
    String name();

    /**
     * 类型
     */
    FieldEnum type() default FieldEnum.STRING;

    /**
     * 是否可以为空
     */
    boolean isNullAble() default true;

    /**
     * 格式，可以是正则，默认为空字符串
     */
    String regex() default "";

    /**
     * 长度(String)
     */
    int length() default -1;

    /**
     * 最大值
     */
    long max() default -1;

    /**
     * 最小值
     */
    long min() default -1;
}