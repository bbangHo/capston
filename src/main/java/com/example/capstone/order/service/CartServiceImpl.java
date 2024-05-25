package com.example.capstone.order.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.order.Cart;
import com.example.capstone.order.dto.CartRequestDTO;
import com.example.capstone.order.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Integer countAllItemInCart(Long memberId){

        return cartRepository.countAllItemInCart(memberId);
    }

    @Override
    public void saveItemInCart(CartRequestDTO.requestedCart cartRequestDTO) {

        Item item = itemRepository.findById(cartRequestDTO.getItemId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.ITEM_NOT_FOUND));

        Member member = memberRepository.findById(cartRequestDTO.getMemberId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Cart cart = Cart.builder()
                .item(item)
                .member(member)
                .quantity(cartRequestDTO.getQuantity())
                .build();

        cartRepository.save(cart);
        log.info("saveItemInCart service success.......");
    }

}
