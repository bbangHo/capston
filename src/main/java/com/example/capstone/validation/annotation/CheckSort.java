package com.example.capstone.validation.annotation;

import com.example.capstone.validation.validator.SortParameterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SortParameterValidator.class)
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSort {

    String message() default "정렬할 열의 값을 정확하게 입력해주세요. 에) name, stock, deadline 등";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
