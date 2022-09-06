package com.github.xzb617.cappuccino.server.validation.validator;

import com.github.xzb617.cappuccino.server.validation.annotation.IsEqualFieldValue;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.Field;

public class IsEqualFieldValueValidator implements ConstraintValidator<IsEqualFieldValue, Object> {

    private String targetField;
    private String sourceField;

    @Override
    public void initialize(IsEqualFieldValue constraintAnnotation) {
        this.targetField = constraintAnnotation.targetField();
        this.sourceField = constraintAnnotation.sourceField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object targetValue = getFieldValue(targetField, object);
            Object sourceValue = getFieldValue(sourceField, object);
            // 都为null
            if (targetValue==null && sourceValue==null) {
                return true;
            }
            // 判断是否一致
            return targetValue!=null && targetValue.equals(sourceValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Field name setting error, cannot compare the values of two fields. cause by " + e.getMessage());
        }
    }

    private Object getFieldValue(String fieldName, Object object)
                        throws IllegalAccessException, NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        Object value = null;
        if (!field.isAccessible()) {
            field.setAccessible(true);
            value = field.get(object);
            field.setAccessible(false);
        } else {
            value =  field.get(object);
        }
        return value;
    }

}
