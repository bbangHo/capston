package com.example.capstone.validation.validator;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.common.QueryService;
import com.example.capstone.validation.annotation.ExistCategories;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriesExistValidator implements ConstraintValidator<ExistCategories, List<Long>> {

    private final QueryService queryService;

    @Override
    public void initialize(ExistCategories constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        boolean isValid = values.stream()
                .allMatch(queryService::categoryIsPresent);

        if (!isValid) {
            context.disableDefaultConstraintViolation();        // 기본 제약 조건 위반을 비활성
            context.buildConstraintViolationWithTemplate(ErrorStatus.ITEM_CATEGORY_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
