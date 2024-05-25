package com.example.capstone.seller.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.common.QueryService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import com.example.capstone.seller.dto.SellerResponseDTO;
import com.example.capstone.seller.service.SellerManagementService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/seller")
@Slf4j
public class SellerManagementController {

    private final SellerManagementService sellerManagementService;
    private final QueryService queryService;

    // TODO: (전체)Fiter기능, 동적쿼리 적용해줘야함, 로그인 완성후 진행
    @GetMapping("/order-status")
    public ApiResponse<SellerResponseDTO.OrderStatusList> getSellerOrderItemStatus(
            @AuthenticationPrincipal MemberSecurityDTO member,
            @Min(1) @RequestParam(name = "page") Integer page,
            @Positive @RequestParam(name = "size") Integer size
    ) {
        Long memberId = member.getId();
        return ApiResponse.onSuccess(sellerManagementService.getSellerOrderItemStatus(memberId, page - 1, size));
    }

    // TODO : 주문_상품 상태 변경 구체화
//    @PatchMapping("/order-status/{order-number}")
//    public ApiResponse<SellerResponseDTO.OrderStatusList>

    @GetMapping("/items")
    public ApiResponse<SellerResponseDTO.SalesItemList> getSalesItems(
            @AuthenticationPrincipal MemberSecurityDTO member,
            @Min(1) @RequestParam(name = "page") Integer page,
            @Positive @RequestParam(name = "size") Integer size
    ) {
        Long memberId = member.getId();
        return ApiResponse.onSuccess(sellerManagementService.getSalesItems(memberId, page - 1, size));
    }

    @GetMapping
    public ApiResponse<SellerResponseDTO.Dashboard> getDashboard(@AuthenticationPrincipal MemberSecurityDTO member) {
        log.info("GET /auth/seller memberId = " + member.getId());

        Long memberId = member.getId();
        return ApiResponse.onSuccess(sellerManagementService.getDashBoard(memberId));
    }
}
