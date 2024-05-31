package com.example.capstone.order.converter;

import com.example.capstone.item.Item;
import com.example.capstone.member.Alarm;
import com.example.capstone.member.Member;
import com.example.capstone.member.converter.AlarmConverter;
import com.example.capstone.member.dto.AlarmResponseDTO;
import com.example.capstone.order.Cart;
import com.example.capstone.order.dto.CartRequestDTO;
import com.example.capstone.order.dto.CartResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.example.capstone.item.converter.ItemConverter.toItemResponseDTO;
import static com.example.capstone.item.converter.ItemConverter.toItemResponseDTOWithSeller;
import static com.example.capstone.member.converter.MemberConverter.*;

public class CartConverter {

    public static CartResponseDTO.Cart toCartResponseDTO(Cart cart) {
        return CartResponseDTO.Cart.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .item(toItemResponseDTOWithSeller(cart.getItem()))
                .build();
    }

}
