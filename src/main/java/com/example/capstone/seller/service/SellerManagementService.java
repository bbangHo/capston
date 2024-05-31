package com.example.capstone.seller.service;

import com.example.capstone.seller.dto.SellerResponseDTO;

public interface SellerManagementService {
    SellerResponseDTO.OrderStatusList getSellerOrderItemStatus(Long memberId, Integer page, Integer size);

    SellerResponseDTO.SalesItemList getSalesItems(Long memberId, Integer page, Integer size);

    SellerResponseDTO.Dashboard getDashBoard(Long memberId);

    SellerResponseDTO.ImminentItemList getImminentItemPage(Long memberId, Integer page, Integer size, String sort, String order);

}
