package com.example.capstone.order.service;


import com.example.capstone.common.QueryService;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.order.Order;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.converter.PaymentConverter;
import com.example.capstone.order.dto.PaymentDTO;
import com.example.capstone.order.repository.OrderItemRepository;
import com.example.capstone.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final QueryService queryService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * 가짜 결제 메소드
     * @param memberId
     * @param request
     * @return
     */
    public PaymentDTO.Response itemPayment(Long memberId, PaymentDTO.Request request) {
        Member member = queryService.findMember(memberId);

        Order order = Order.builder()
                .member(member)
                .build();

        order = orderRepository.save(order);

        for (PaymentDTO.itemInfo itemInfo : request.getItemInfoList()) {
            Item item = queryService.findItem(itemInfo.getItemId());

            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .order(order)
                    .quantity(itemInfo.getQuantity())
                    .build();

            orderItem = orderItemRepository.save(orderItem);
        }

        return PaymentConverter.toResponse(order);
    }
}
