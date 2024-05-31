package com.example.capstone.order.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.order.dto.PaymentDTO;
import com.example.capstone.order.service.PaymentService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/auth/payment")
    public ApiResponse<PaymentDTO.Response> itemPayment(@AuthenticationPrincipal MemberSecurityDTO member,
                                      @RequestBody PaymentDTO.Request request) {
        PaymentDTO.Response response = paymentService.itemPayment(member.getId(), request);
        return ApiResponse.of(SuccessStatus._OK_PAYMENT, response);
    }
}
