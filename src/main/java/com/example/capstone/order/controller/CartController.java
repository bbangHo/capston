package com.example.capstone.order.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.order.dto.CartRequestDTO;
import com.example.capstone.order.service.CartService;
import com.example.capstone.order.service.CartServiceImpl;
import com.example.capstone.util.AuthenticatedMemberUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/cart")
public class CartController {

    private final CartService cartServiceImpl;

    @GetMapping("/{memberId}")
    public ApiResponse<Integer> getAllItemInCartNum(@PathVariable Long memberId) {
        return ApiResponse.onSuccess(cartServiceImpl.countAllItemInCart(memberId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> saveItemInCart(@Valid @RequestBody CartRequestDTO.requestedCart requestedCart, BindingResult bindingResult) throws BindException {
        log.info("CartController start.........");

        if(bindingResult.hasErrors()) {
            log.info("bindException...............");
        }


        requestedCart.setMemberId(AuthenticatedMemberUtil.getMemberId());
        cartServiceImpl.saveItemInCart(requestedCart);

        return ApiResponse.of(SuccessStatus._OK_SUBSCRIBE, "장바구니에 담기 완료");
    }
}
