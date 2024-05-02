package com.example.capstone.order.service;

import com.example.capstone.order.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public Integer countAllItemInCart(Long memberId){

        return cartRepository.countAllItemInCart(memberId);
    }
}
