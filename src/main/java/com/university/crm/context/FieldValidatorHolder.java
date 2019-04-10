package com.university.crm.context;


import com.university.crm.annotation.FieldNote;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * date: 2017/8/18
 * description : 属性验证处理器
 *
 * @author : zhencai.cheng
 */
public class FieldValidatorHolder {

    public static final IValidator<String> STRING = newStringVal();

    public static final IValidator<Number> NUMBER = newNumber();

    public static final IValidator<Date> DATE = newDate();
    public static final IValidator<Collection> COLLECTION = newCollection();


    public static final IValidator<String> DATE_STR = (note, fieldName, fieldValue, result) -> {
        result = checkNullAndLen(note, fieldName, fieldValue, result);
        if (!result.isSuccess()) {
            return result;
        }
        if (!StringUtils.isEmpty(fieldValue)
                && !StringUtils.isEmpty(note.regex())) {
            if (!validateDate(fieldValue, note.regex())) {
                result.addError(note.name() + "[" + fieldName + "]值["
                        + fieldValue + "]日期不合法或不能匹配格式 " + note.regex());
                return result;
            }
        }
        return result;
    };

    private static IValidator<Collection> newCollection() {
        return (note, fieldName, fieldValue, result) -> {
            checkNullAndEmpty(note, fieldName, fieldValue, result);
            return result;
        };
    }

    /**
     * 检验空 比较大小
     *
     * @param note       注解
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param result     验证结果
     */
    private static void checkNullAndEmpty(FieldNote note, String fieldName, Collection fieldValue, ValidatorResult result) {
        if (!note.isNullAble() && (fieldValue == null || fieldValue.isEmpty())) {
            result.addError(note.name() + "[" + fieldName + "]为空！");
        }
    }


    private static IValidator<Date> newDate() {
        return (note, fieldName, fieldValue, result) -> {
            checkNullComparator(note, fieldName, fieldValue, result);
            return result;
        };
    }

    /**
     * 检验空 比较大小
     *
     * @param note       注解
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param result     验证结果
     */
    private static void checkNullComparator(FieldNote note, String fieldName, Date fieldValue, ValidatorResult result) {
        if (!note.isNullAble() && fieldValue == null) {
            result.addError(note.name() + "[" + fieldName + "]为空！");
            return;
        }
        if (note.max() > -1 && note.max() < fieldValue.getTime()) {
            result.addError(note.name() + "[" + fieldName + "]不能大于最大值！");
            return;
        }
        if (note.min() > -1 && fieldValue.getTime() < note.min()) {
            result.addError(note.name() + "[" + fieldName + "]不能小于最小值！");
        }
    }


    private static IValidator<Number> newNumber() {
        return (note, fieldName, fieldValue, result) -> {

            checkNullComparator(note, fieldName, fieldValue, result);
            return result;
        };
    }

    /**
     * 检验空 比较大小
     *
     * @param note       注解
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param result     验证结果
     */
    private static void checkNullComparator(FieldNote note, String fieldName, Number fieldValue, ValidatorResult result) {
        if (!note.isNullAble() && fieldValue == null) {
            result.addError(note.name() + "[" + fieldName + "]为空！");
            return;
        }
        if (note.max() > -1 && note.max() < fieldValue.longValue()) {
            result.addError(note.name() + "[" + fieldName + "]不能大于最大值！");
            return;
        }
        if (note.min() > -1 && fieldValue.longValue() < note.min()) {
            result.addError(note.name() + "[" + fieldName + "]不能小于最小值！");
        }
    }


    private static IValidator<String> newStringVal() {
        return (note, fieldName, fieldValue, result) -> {
            result = checkNullAndLen(note, fieldName, fieldValue, result);
            if (!result.isSuccess()) {
                return result;
            }
            if (!StringUtils.isEmpty(fieldValue)
                    && !StringUtils.isEmpty(note.regex())
                    && !fieldValue.matches(note.regex())) {
                result.addError(note.name() + "[" + fieldName + "]值["
                        + fieldValue + "]不能匹配格式 " + note.regex() + " ");
                return result;
            }
            return result;
        };
    }

    /**
     * 检验空 比较大小
     *
     * @param note       注解
     * @param fieldName  属性名
     * @param fieldValue 属性值
     * @param result     验证结果
     */
    private static ValidatorResult checkNullAndLen(FieldNote note, String fieldName, String fieldValue, ValidatorResult result) {
        if (StringUtils.isEmpty(fieldValue)) {
            if (!note.isNullAble()) {
                result.addError(note.name() + "[" + fieldName + "]为空！");
            }
            return result;
        }
        if (note.length() > 0 && (fieldValue.length() > note.length())) {
            result.addError(note.name() + "[" + fieldName + "]长度("
                    + fieldValue.length() + ")超过限制(" + note.length() + ")");
            return result;
        }
        return result;
    }

    private static boolean validateDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = dateFormat.parse(dateStr);
            String pStr = dateFormat.format(date);
            return pStr.equals(dateStr);
        } catch (ParseException e) {
            return false;
        }
    }

}