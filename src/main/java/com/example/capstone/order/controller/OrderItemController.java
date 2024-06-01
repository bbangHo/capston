package com.example.capstone.order.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.order.dto.OrderItemResponseDTO;
import com.example.capstone.order.service.OrderItemService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/orderItems")
    public ApiResponse<OrderItemResponseDTO.OrderList> searchOrderItems (@Valid @AuthenticationPrincipal MemberSecurityDTO member,
                                                                          @Min(1) @RequestParam(name = "page") Integer page,
                                                                          @Positive @RequestParam(name = "size") Integer size) {

        log.info("Search orderItems controller ............");

        return ApiResponse.onSuccess(orderItemService.searchOrderItems(member.getId(),page-1, size));
    }

    @GetMapping("/orderGroupItems")
    public ApiResponse<OrderItemResponseDTO.OrderListWithSum> searchOrderGroupItems (@Valid @AuthenticationPrincipal MemberSecurityDTO member,
                                                                         @Min(1) @RequestParam(name = "page") Integer page,
                                                                         @Positive @RequestParam(name = "size") Integer size) {

        log.info("Search orderGroupItems controller ............");

        return ApiResponse.onSuccess(orderItemService.searchOrderGroupItems(member.getId(),page-1, size));
    }

}
