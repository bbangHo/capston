package com.example.capstone.order.service;

import com.example.capstone.order.dto.CartRequestDTO;
import com.example.capstone.order.dto.CartResponseDTO;

public interface CartService {

    Integer countAllItemInCart(Long memberId);

    void saveItemInCart(CartRequestDTO.requestedCart cartRequestDTO);

    CartResponseDTO.CartResult getItemsInCart(Long memberId);

    void deleteItem(Long memberId, Long id);

}
