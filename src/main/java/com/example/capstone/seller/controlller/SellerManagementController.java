package com.example.capstone.seller.controlller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.seller.dto.SellerResponseDTO;
import com.example.capstone.seller.service.SellerManagementService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerManagementController {

    private final SellerManagementService sellerManagementService;

    // TODO: (전체)Fiter기능, 동적쿼리 적용해줘야함, 로그인 완성후 진행
    @GetMapping("/order-status")
    public ApiResponse<SellerResponseDTO.OrderStatusList> getSellerOrderItemStatus(
            @Positive @RequestParam(name = "seller-id") Long sellerId,
            @Min(1) @RequestParam(name = "page") Integer page,
            @Positive @RequestParam(name = "size") Integer size
    ) {
        return ApiResponse.onSuccess(sellerManagementService.getSellerOrderItemStatus(sellerId, page - 1, size));
    }

    // TODO : 주문_상품 상태 변경 구체화
//    @PatchMapping("/order-status/{order-number}")
//    public ApiResponse<SellerResponseDTO.OrderStatusList>

    @GetMapping("/items")
    public ApiResponse<SellerResponseDTO.SalesItemList> getSalesItems(
            @Positive @RequestParam(name = "seller-id") Long sellerId,
            @Min(1) @RequestParam(name = "page") Integer page,
            @Positive @RequestParam(name = "size") Integer size
    ) {
        return ApiResponse.onSuccess(sellerManagementService.getSalesItems(sellerId, page - 1, size));
    }

    @GetMapping
    public ApiResponse<SellerResponseDTO.Dashboard> getDashboard(@Positive @RequestParam(name = "seller-id") Long sellerId) {
        return ApiResponse.onSuccess(sellerManagementService.getDashBoard(sellerId));
    }
}
