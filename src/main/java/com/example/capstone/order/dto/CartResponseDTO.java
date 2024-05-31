package com.example.capstone.order.dto;


import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.member.repository.MemberRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartResponseDTO {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cart {

        private Long id;

        private ItemResponseDTO.ItemWithSeller item;

        private Integer quantity;

    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartResult {

        private Integer sumDeliveryCharge;

        private List<CartResponseDTO.Cart> carts;

    }

}
