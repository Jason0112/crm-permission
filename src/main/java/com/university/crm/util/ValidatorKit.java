package com.university.crm.util;


import com.university.crm.annotation.FieldNote;
import com.university.crm.context.ValidatorResult;
import com.university.crm.exception.BusinessException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * date: 2018/8/18
 * description : 对象属性验证工具
 *
 * @author : zhencai.cheng
 */
public final class ValidatorKit {

    private ValidatorKit() {
        throw new IllegalArgumentException("argument exception");
    }

    public static ValidatorResult validator(Object o) {
        ValidatorResult result = new ValidatorResult();
        if (o == null) {
            result.setSuccess(false);
            result.addError("对象不能为空");
            return result;
        }
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field f : fields) {
            Annotation[] annotations = f.getAnnotations();
            if (annotations == null || annotations.length == 0) {
                continue;
            }
            Annotation a = annotations[0];
            if (a instanceof FieldNote) {
                FieldNote note = f.getAnnotation(FieldNote.class);
                note.type().validate(note, f.getName(), getValue(o, f.getName()), result);
            }
        }
        return result;
    }

    public static void validation(Object o) throws BusinessException {
        ValidatorResult result = validator(o);
        if (!result.isSuccess()) {
            throw new BusinessException(result.getErrorStr(), true);
        }
    }

    private static Object getValue(Object o, String fieldName) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
