package com.example.capstone.order.service;

import com.example.capstone.order.OrderItem;
import com.example.capstone.order.converter.OrderItemConverter;
import com.example.capstone.order.dto.OrderItemResponseDTO;
import com.example.capstone.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.capstone.item.converter.ItemConverter.toItemResponseDTO;
import static com.example.capstone.order.converter.OrderItemConverter.toItemStatusListDTO;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderItemServiceImpl implements OrderItemService{

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItemResponseDTO.OrderList searchOrderItems(Long memberId, Integer page, Integer size){
        log.info("OrderItems search service start..........");

        Page<OrderItem> orderItems = orderItemRepository.searchOrdersByMember(memberId, PageRequest.of(page, size, Sort.by("createdAt").descending()));

        return toItemStatusListDTO(orderItems);

    }

    @Override
    public OrderItemResponseDTO.OrderListWithSum searchOrderGroupItems(Long memberId, Integer page, Integer size){

        log.info("OrderItems search service start..........");

        Page<OrderItem> orderItems = orderItemRepository.searchGroupOrderSum(memberId, PageRequest.of(page, size, Sort.by("createdAt").descending()));

        List<OrderItemResponseDTO.OrderItemWithSum> groupItems = new ArrayList<>();

        for(OrderItem item: orderItems.getContent()){

            Integer sum = orderItemRepository.searchGroupOrderSum(item.getId());

            groupItems.add(OrderItemResponseDTO.OrderItemWithSum.builder()
                    .id(item.getId())
                    .orderId(item.getOrder().getId())
                    .item(toItemResponseDTO(item.getItem()))
                    .status(item.getStatus())
                    .quantity(item.getQuantity())
                    .createdAt(item.getCreatedAt())
                    .targetQuantity(item.getItem().getGroupPurchaseItem().getTargetQuantity())
                    .currentSum(sum)
                    .build());
        }

        return OrderItemResponseDTO.OrderListWithSum.builder()
                .listSize(orderItems.getSize())
                .totalElement(orderItems.getTotalElements())
                .isFirst(orderItems.isFirst())
                .isLast(orderItems.isLast())
                .orderGroupItemList(groupItems)
                .build();

    }

}
