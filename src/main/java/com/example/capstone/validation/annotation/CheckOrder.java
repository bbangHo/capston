package com.example.capstone.validation.annotation;

import com.example.capstone.validation.validator.OrderParameterValidator;
import com.example.capstone.validation.validator.SortParameterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrderParameterValidator.class)
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckOrder {

    String message() default "정렬할 순서의 값을 정확하게 입력해주세요. 예) asc, ASC, desc, DESC";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
