package com.example.capstone.seller.service;

import com.example.capstone.seller.dto.SellerResponseDTO;

public interface SellerManagementService {
    SellerResponseDTO.OrderStatusList getSellerOrderItemStatus(Integer page, Integer size, Long sellerId);
}
