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
    MEMBER_ALREADY_SELLER(HttpStatus.BAD_REQUEST, "MEMBER_400_3", "해당 사용자는 이미 판매자입니다."),
    DUP_CHECK_FIELD_BADTYPE(HttpStatus.BAD_REQUEST, "MEMBER_400_4","중복 체크할 필드 타입이 잘못되었습니다."),
    MALFORMED_MEMBER_LOGINID(HttpStatus.BAD_REQUEST, "MEMBER_400_5", "ID 형식에 문제가 있습니다."),
    MALFORMED_MEMBER_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_400_6", "비밀번호 형식에 문제가 있습니다."),
    MALFORMED_MEMBER_NAME(HttpStatus.BAD_REQUEST, "MEMBER_400_7", "사용자의 이름 형식에 문제가 있습니다."),
    MALFORMED_MEMBER_NICKNAME(HttpStatus.BAD_REQUEST, "MEMBER_400_8", "사용자의 닉네임 형식에 문제가 있습니다."),
    MALFORMED_MEMBER_PHONE(HttpStatus.BAD_REQUEST, "MEMBER_400_9", "사용자의 전화번호 형식에 문제가 있습니다."),
    TEMP_MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_400_10", "임시 멤버가 아닙니다."),


    // Seller
    SELLER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "SELLER_401_1", "해당 사용자는 판매자 권한이 없습니다."),

    // paging
    PAGE_NUMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PAGE_NUMBER_400_1", "페이지 번호는 1 이상이어야 합니다."),

    // 카테고리
    ITEM_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_400_1", "상품 카테고리 id는 1 이상 ? 이하입니다."),

    // 구독
    ALREADY_SUBSCRIBED(HttpStatus.BAD_REQUEST, "SUBSCRIBED_400_1", "이미 구독한 사용자입니다."),
    ALREADY_UNSUBSCRIBED(HttpStatus.BAD_REQUEST, "SUBSCRIBED_400_2", "이미 구독을 취소한 사용자입니다."),

    // image
    IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IMAGE_500_1", "이미지 업로드에 실패했습니다."),

    // order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_400_1", "주문번호에 해당하는 주문이 없습니다."),

    // sort
    SORT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "SORT_400_1", "sort의 value가 잘못 입력되었습니다."),

    //item
    ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "ITEM_400_1", "해당되는 상품을 조회할 수 없습니다."),
    ITEM_UPLOAD_BAD_REQUEST(HttpStatus.BAD_REQUEST, "ITEM_400_2", "요청에서 '공동 구매 목표 인원' 혹은 '공동 구매 가격' 이 빠졌습니다."),

    // post
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "POST_400_1", "게시글이 존재하지 않습니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_403_1", "게시글을 수정할 권한이 없거나, 게시글 작성자가 아닙니다."),

    //Token
    ACCESS_TOKEN_NOT_ACCEPTED(HttpStatus.UNAUTHORIZED, "Jwt_400_1", "Access Token이 존재하지 않습니다."),
    ACCESS_TOKEN_BADTYPE(HttpStatus.UNAUTHORIZED, "Jwt_400_2", "Access Token의 타입이 bearer가 아닙니다."),
    MALFORMED_ACCESS_TOKEN(HttpStatus.FORBIDDEN, "JWT_400_3", "Access Token의 값이 올바르게 설정되지 않았습니다. "),
    BAD_SIGNED_ACCESS_TOKEN(HttpStatus.FORBIDDEN, "JWT_400_4", "Access Token의 서명이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.FORBIDDEN, "JWT_400_5", "Access Token이 만료되었습니다."),
    MALFORMED_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "JWT_400_7", "Refresh Token의 값이 올바르게 설정되지 않았습니다. "),
    EXPIRED_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "JWT_400_8", "Refresh Token이 만료되었습니다."),
    TOKENS_NOT_ACCEPTED(HttpStatus.UNAUTHORIZED, "Jwt_400_9", "Access Token과 refresh Token이 존재하지 않습니다."),



    //Login
    ID_AND_PASSWORD_NOT_MATCH(HttpStatus.FORBIDDEN,"LOGIN_400_1","ID 또는 비밀번호가 잘못되었습니다."),
    LOGIN_DATA_NOT_ACCEPTED(HttpStatus.UNAUTHORIZED,"LOGIN_400_2","로그인 ID와 비밀번호가 비어있습니다."),
    MALFORMED_LOGIN_DATA(HttpStatus.UNAUTHORIZED,"LOGIN_400_3","로그인 ID와 비밀번호을 전달하는 JSON 형식에 문제가 있습니다."),

    //signUp
    DUPLICATED_LOGINID(HttpStatus.BAD_REQUEST,"DUPCHECK_400_1","중복된 로그인 ID입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST,"DUPCHECK_400_2","중복된 닉네임입니다."),
    NOT_CHECKED_LOGINID(HttpStatus.BAD_REQUEST,"DUPCHECK_400_3","중복 체크된 아이디가 아닙니다."),
    NOT_CHECKED_NICKNAME(HttpStatus.BAD_REQUEST,"DUPCHECK_400_4","중복 체크된 닉네임이 아닙니다."),

    //address
    ADDRESS_NOT_FOUND(HttpStatus.BAD_REQUEST,"ADDRESS_400_1","해당 주소를 찾을 수 없습니다."),

    //cart
    CART_NOT_MATCH_MEMBER(HttpStatus.FORBIDDEN,"ADDRESS_403_1","해당 장바구니는 현재 멤버의 장바구니가 아닙니다.")

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
