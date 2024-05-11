package com.example.capstone.seller.service;

import com.example.capstone.seller.dto.SellerResponseDTO;

public interface SellerManagementService {
    SellerResponseDTO.OrderStatusList getSellerOrderItemStatus(Long sellerId, Integer page, Integer size);

    SellerResponseDTO.SalesItemList getSalesItems(Long sellerId, Integer page, Integer size);
}
