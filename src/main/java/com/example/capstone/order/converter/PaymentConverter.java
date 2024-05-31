package com.example.capstone.order.converter;

import com.example.capstone.order.Order;
import com.example.capstone.order.dto.PaymentDTO;

public class PaymentConverter {


    public static PaymentDTO.Response toResponse(Order order) {
        return PaymentDTO.Response.builder()
                .orderId(order.getId())
                .build();
    }
}
