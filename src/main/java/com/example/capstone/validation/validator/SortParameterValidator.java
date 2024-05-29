package com.example.capstone.validation.validator;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.validation.annotation.CheckSortParameter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SortParameterValidator implements ConstraintValidator<CheckSortParameter, List<String>> {

    static List<String> option = Arrays.asList("name", "stock", "deadline");

    @Override
    public void initialize(CheckSortParameter constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        // name, stock, deadline
        boolean isValid = values.stream()
                .anyMatch(s -> option.contains(s));

        if (!isValid) {
            context.disableDefaultConstraintViolation();        // 기본 제약 조건 위반을 비활성
            context.buildConstraintViolationWithTemplate(ErrorStatus.SORT_BAD_REQUEST.toString()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
