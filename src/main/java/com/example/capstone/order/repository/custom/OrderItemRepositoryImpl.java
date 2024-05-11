package com.example.capstone.order.repository.custom;

import com.example.capstone.order.OrderItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.example.capstone.order.QOrderItem.orderItem;
import static com.example.capstone.item.QItem.item;

@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {
    private JPAQueryFactory queryFactory;

    @Override
    public Page<OrderItem> getSellerOrderItemStatus(Long sellerId, Pageable pageable) {
        List<OrderItem> orderItemJPAQuery = queryFactory
                .select(orderItem)
                .from(orderItem)
                .leftJoin(item)
                .on(orderItem.item.id.eq(item.id))
                .where(item.member.id.eq(sellerId))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(orderItem.count())
                .from(orderItem)
                .leftJoin(item)
                .on(orderItem.item.id.eq(item.id))
                .where(item.member.id.eq(sellerId));

        return PageableExecutionUtils.getPage(orderItemJPAQuery, pageable, count::fetchOne);
    }
}
