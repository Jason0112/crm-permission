package com.university.crm.context;


import com.university.crm.annotation.FieldNote;

/**
 * date: 2017/8/7
 * description : 验证接口
 *
 * @author : zhencai.cheng
 */
@FunctionalInterface
public interface IValidator<T> {

    ValidatorResult validate(FieldNote note, String fieldName, T fieldValue, ValidatorResult result);
}