package com.university.crm.enums;


import com.university.crm.annotation.FieldNote;
import com.university.crm.context.FieldValidatorHolder;
import com.university.crm.context.IValidator;
import com.university.crm.context.ValidatorResult;

/**
 * date: 2018/8/18
 * description :
 *
 * @author : zhencai.cheng
 */
public enum FieldEnum {
    DATE_STR(FieldValidatorHolder.DATE_STR),
    STRING(FieldValidatorHolder.STRING),
    NUMBER(FieldValidatorHolder.NUMBER),
    DATE(FieldValidatorHolder.DATE),
    COLLECTION(FieldValidatorHolder.COLLECTION);


    private IValidator validator;

    FieldEnum(IValidator validator) {
        this.validator = validator;
    }

    public IValidator getValidator() {
        return validator;
    }

    public <T> ValidatorResult validate(FieldNote note, String fieldName, T fieldVal, ValidatorResult error) {
        return validator.validate(note, fieldName, fieldVal, error);
    }
}
