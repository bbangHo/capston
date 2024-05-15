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
    _OK_SUBSCRIBE(HttpStatus.OK, "SUBSCRIBE_200", "정상적으로 구독이 되었습니다."),
    _OK_UNSUBSCRIBE(HttpStatus.OK, "UNSUBSCRIBE_200", "정상적으로 구독이 취소 되었습니다.")
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
