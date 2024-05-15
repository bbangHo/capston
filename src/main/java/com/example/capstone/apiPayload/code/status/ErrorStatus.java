package com.example.capstone.apiPayload.code.status;

import com.example.capstone.apiPayload.code.BaseErrorCode;
import com.example.capstone.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 관리자에게 문의 바랍니다."),

    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON_400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON_401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),

    // Member 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_400_1", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER_400_2", "닉네임은 필수 입니다."),

    // paging
    PAGE_NUMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PAGE_NUMBER_400_1", "페이지 번호는 1 이상이어야 합니다."),

    // 카테고리
    ITEM_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_400_1", "상품 카테고리 id는 1 이상 ? 이하입니다."),

    // 구독
    ALREADY_SUBSCRIBED(HttpStatus.BAD_REQUEST, "SUBSCRIBED_400_1", "이미 구독한 사용자입니다."),
    ALREADY_UNSUBSCRIBED(HttpStatus.BAD_REQUEST, "SUBSCRIBED_400_2", "이미 구독을 취소한 사용자입니다."),

    //item
    ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "ITEM_400_1", "해당되는 상품을 조회할 수 없습니다.")
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .message(message)
                .code(code)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .isSuccess(false)
                .message(message)
                .code(code)
                .httpStatus(httpStatus)
                .build();
    }
}
