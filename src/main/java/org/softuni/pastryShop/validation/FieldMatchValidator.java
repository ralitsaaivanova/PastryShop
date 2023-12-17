package org.softuni.pastryShop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,Object> {

    private String first;
    private String second;
    private String message;
    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.second = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object firstFieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(first);
        Object secondFieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(second);

        if(firstFieldValue!=null){
            return firstFieldValue.equals(secondFieldValue);
        }

        return secondFieldValue == null;
//        BeanWrapper beanWrapper = PropertyAccessorFactory
//                .forBeanPropertyAccess(value);
//        Object firstProperty = beanWrapper.getPropertyValue(this.first);
//        Object secondProperty = beanWrapper.getPropertyValue(this.second);
//
//        boolean isValid =  Objects.equals(firstProperty, secondProperty);
//
//        if (!isValid) {
//            context
//                    .buildConstraintViolationWithTemplate(message)
//                    .addPropertyNode(second)
//                    .addConstraintViolation()
//                    .disableDefaultConstraintViolation();
//        }
//
//        return isValid;
    }
}
