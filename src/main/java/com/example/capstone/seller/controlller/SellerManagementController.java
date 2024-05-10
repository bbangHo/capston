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

    @GetMapping("/order-status")
    public ApiResponse<SellerResponseDTO.OrderStatusList> getSellerOrderItemStatus(
            @RequestParam(name = "seller-id") Long sellerId,
            @Min(1) @RequestParam(name = "page") Integer page,
            @Positive @RequestParam(name = "size") Integer size
    ) {
        return ApiResponse.onSuccess(sellerManagementService.getSellerOrderItemStatus(sellerId, page - 1, size));
    }
}
