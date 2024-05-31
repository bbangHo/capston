package com.example.capstone.order.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.order.dto.CartRequestDTO;
import com.example.capstone.order.dto.CartResponseDTO;
import com.example.capstone.order.service.CartService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{memberId}")
    public ApiResponse<Integer> getAllItemInCartNum(@PathVariable Long memberId) {
        return ApiResponse.onSuccess(cartService.countAllItemInCart(memberId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> saveItemInCart(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO, @Valid @RequestBody CartRequestDTO.requestedCart requestedCart, BindingResult bindingResult) throws BindException {
        log.info("CartController start.........");

        if(bindingResult.hasErrors()) {
            log.info("bindException...............");
        }

        //로그인한 유저의 정보를 securityContextHoler에서 불러옵니다.
        requestedCart.setMemberId(memberSecurityDTO.getId());

        cartService.saveItemInCart(requestedCart);
        log.info("saveItemInCart success.........");

        return ApiResponse.of(SuccessStatus._OK_ADD_ITEM_IN_CART, null);
    }

    @GetMapping("/item")
    public ApiResponse<CartResponseDTO.CartResult> getAllItemInCart(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO) {

        log.info("getAllItemInCart start...............");
        Long memberId = memberSecurityDTO.getId();

        return ApiResponse.onSuccess(cartService.getItemsInCart(memberId));
    }

    @DeleteMapping("/{cartId}")
    public ApiResponse<String> deleteItemInCart(@AuthenticationPrincipal MemberSecurityDTO memberSecurityDTO, @PathVariable Long cartId) {

        log.info("deleteItemInCart controller start ...............");

        Long memberId = memberSecurityDTO.getId();

        cartService.deleteItem(memberId, cartId);

        log.info("deleteItemInCart success...............");

        return ApiResponse.of(SuccessStatus._OK_DELETE_ITEM_IN_CART, null);

    }
}
