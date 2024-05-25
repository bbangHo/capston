package com.example.capstone.order.service;

import com.example.capstone.order.dto.CartRequestDTO;

public interface CartService {

    Integer countAllItemInCart(Long memberId);

    void saveItemInCart(CartRequestDTO.requestedCart cartRequestDTO);
}
