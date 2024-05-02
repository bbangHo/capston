package com.example.capstone.order.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{memberId}")
    public ApiResponse<Integer> getAllItemInCartNum(@PathVariable Long memberId) {

        return ApiResponse.onSuccess(cartService.countAllItemInCart(memberId));
    }
}
