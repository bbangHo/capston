package com.example.capstone.apiPayload.code.status;

import com.example.capstone.apiPayload.code.BaseCode;
import com.example.capstone.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    _OK(HttpStatus.OK, "200", "정상적인 요청."),

    // 구독
    _OK_SUBSCRIBE(HttpStatus.OK, "SUBSCRIBE_200_1", "정상적으로 구독이 되었습니다."),
    _OK_UNSUBSCRIBE(HttpStatus.OK, "SUBSCRIBE_200_2", "정상적으로 구독이 취소 되었습니다."),
    _OK_ALREADY_SUBSCRIBED(HttpStatus.OK, "SUBSCRIBE_200_3", "이미 구독 중인 사용자입니다."),
    _OK_NOT_SUBSCRIBED(HttpStatus.OK, "SUBSCRIBE_200_4", "구독하고 있지 않은 사용자입니다."),

    // 권한
    _OK_CHANGE_ROLE(HttpStatus.OK, "ROLE_200", "판매자로 전환 되었습니다."),

    //CART
    _OK_ADD_ITEM_IN_CART(HttpStatus.OK, "CART_200_1", "정상적으로 장바구니에 추가되었습니다."),

    //POST
    _OK_GET_POST(HttpStatus.OK, "POST_200_1", "정상적으로 게시글이 조회되었습니다."),
    _OK_CREATE_POST(HttpStatus.OK, "POST_200_2", "정상적으로 게시글이 생성되었습니다."),
    _OK_UPDATE_POST(HttpStatus.OK, "POST_200_3", "정상적으로 게시글이 수정되었습니다."),
    _OK_DELETE_POST(HttpStatus.OK, "POST_200_4", "정상적으로 게시글이 삭제되었습니다."),

    //SINGUP
    _OK_DUP_CHECK(HttpStatus.OK, "SIGNUP_200_1", "정상적으로 중복체크되었습니다."),
    _OK_DELETE_TEMP_MEMBER(HttpStatus.OK,"SIGNUP_200_2","정상적으로 임시 멤버를 제거했습니다."),
    _OK_SIGNUP(HttpStatus.OK,"SIGNUP_200_3","정상적으로 회원가입이 완료되었습니다."),

    //Member
    _OK_CHANGE_NICKNAME(HttpStatus.OK,"MEMBER_200_1","정상적으로 닉네임이 변경되었습니다.."),
    _OK_CHANGE_MEMBER_DATA(HttpStatus.OK,"MEMBER_200_2","정상적으로 회원정보가 수정되었습니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .isSuccess(true)
                .message(message)
                .code(code)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .isSuccess(true)
                .message(message)
                .code(code)
                .httpStatus(httpStatus)
                .build();
    }
}
