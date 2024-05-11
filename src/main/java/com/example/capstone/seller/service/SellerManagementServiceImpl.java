package com.example.capstone.seller.service;

import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.repository.OrderItemRepository;
import com.example.capstone.seller.converter.SellerManagementConverter;
import com.example.capstone.seller.dto.SellerResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SellerManagementServiceImpl implements SellerManagementService {
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @Override
    public SellerResponseDTO.OrderStatusList getSellerOrderItemStatus(Long sellerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItem> sellerOrderItemStatusPage = orderItemRepository.getSellerOrderItemStatus(sellerId, pageable);
        return SellerManagementConverter.toOrderStatusList(sellerOrderItemStatusPage);
    }

    @Override
    public SellerResponseDTO.SalesItemList getSalesItems(Long sellerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> itemPage = itemRepository.findByMemberId(sellerId, pageable);
        return SellerManagementConverter.toSalesItemList(itemPage);
    }
}
