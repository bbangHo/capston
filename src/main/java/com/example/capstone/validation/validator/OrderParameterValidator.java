package com.example.capstone.validation.validator;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.validation.annotation.CheckSort;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderParameterValidator implements ConstraintValidator<CheckSort, String> {
    static List<String> option = Arrays.asList("desc", "DESC", "asc", "ASC");

    @Override
    public void initialize(CheckSort constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String values, ConstraintValidatorContext context) {
        // name, stock, deadline
        boolean isValid = option.contains(values);

        if (!isValid) {
            context.disableDefaultConstraintViolation();        // 기본 제약 조건 위반을 비활성
            context.buildConstraintViolationWithTemplate(ErrorStatus.SORT_BAD_REQUEST.toString()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
