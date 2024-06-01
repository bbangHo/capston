package com.example.capstone.order.service;

import com.example.capstone.order.dto.OrderItemResponseDTO;

public interface OrderItemService {

   OrderItemResponseDTO.OrderList searchOrderItems(Long memberId, Integer page, Integer size);

   OrderItemResponseDTO.OrderListWithSum searchOrderGroupItems(Long memberId, Integer page, Integer size);

}
