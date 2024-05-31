package com.example.capstone.order.repository.custom;

import com.example.capstone.order.OrderItem;
import com.example.capstone.order.common.DateType;
import com.example.capstone.order.common.OrderStatus;
import com.example.capstone.order.dto.MonthlySalesVolumeDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import static com.example.capstone.item.QItem.item;
import static com.example.capstone.order.QOrderItem.orderItem;

@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * 판매자의 주문 현황을 반환합니다
     * @param sellerId
     * @param pageable
     * @return
     */
    @Override
    public List<MonthlySalesVolumeDTO> getMonthlySalesVolume1Year(Long sellerId) {
        LocalDateTime thisYear = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);
        LocalDateTime lastYear = thisYear.minusYears(1).withDayOfMonth(1).toLocalDate().atStartOfDay();

        StringTemplate month = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m')", orderItem.createdAt);

        return queryFactory
                .select(Projections.constructor(
                            MonthlySalesVolumeDTO.class,
                            month.as("month"),
                            orderItem.quantity.multiply(item.price).sum().as("salesVolume")
                        )
                )
                .from(orderItem)
                .leftJoin(item)
                .on(orderItem.item.id.eq(item.id))
                .where(
                        orderItem.createdAt.between(lastYear, thisYear),
                        orderItem.status.eq(OrderStatus.DELIVERED),
                        item.seller.id.eq(sellerId)
                )
                .groupBy(month)
                .orderBy(month.asc())
                .fetch();
    }


    /**
     * sellerId에 해당하는 판매자의 상품 중 새로 주문이 들어온 상품의 개수를 조회합니다
     * @param sellerId
     * @return
     */
    @Override
    public Long getOrderStatusNumber(Long sellerId) {
        return queryFactory
                .select(orderItem.count())
                .from(orderItem)
                .where(orderItem.item.seller.id.eq(sellerId))
                .groupBy(orderItem.status)
                .having(orderItem.status.eq(OrderStatus.PENDING))
                .fetchOne();
    }

    @Override
    public Page<OrderItem> getSellerOrderItemStatus(Long sellerId, Pageable pageable) {
        List<OrderItem> orderItemJPAQuery = queryFactory
                .select(orderItem)
                .from(orderItem)
                .leftJoin(item)
                .on(orderItem.item.id.eq(item.id))
                .where(item.seller.id.eq(sellerId))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(orderItem.count())
                .from(orderItem)
                .leftJoin(item)
                .on(orderItem.item.id.eq(item.id))
                .where(item.seller.id.eq(sellerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(orderItemJPAQuery, pageable, count::fetchOne);
    }

    /**
     * 판매자의 특정기간 동안의 상품 판매량을 반환합니다.
     *
     * @param sellerId
     * @param dateType
     * @return
     */
    @Override
    public List<Integer> getSalesVolume(Long sellerId, DateType dateType) {
        return queryFactory
                .select(item.price.multiply(orderItem.quantity))
                .from(orderItem).leftJoin(item)
                .on(orderItem.item.id.eq(item.id))
                .where(
                        orderItem.item.seller.id.eq(sellerId),
                        localDateTimeFilter(dateType)
                )
                .groupBy(item.id).fetch();
    }


    private BooleanExpression localDateTimeFilter(DateType dateType) {
        LocalDateTime start = null;
        LocalDateTime end = null;

        if (dateType == DateType.DAY || dateType == DateType.DAY_BEFORE) {
            LocalDate today = dateType == DateType.DAY ? LocalDate.now() : LocalDate.now().minusDays(1);
            start = LocalDateTime.of(today, LocalTime.MIN);
            end = LocalDateTime.of(today, LocalTime.MAX).withNano(0);
        }

        if (dateType == DateType.MONTH || dateType == DateType.LAST_MONTH) {
            YearMonth thisMonth = dateType == DateType.MONTH ? YearMonth.now() : YearMonth.now().minusMonths(1);
            start = thisMonth.atDay(1).atStartOfDay();
            end = thisMonth.atEndOfMonth().atTime(23, 59, 59);
        }

        return Expressions.allOf(orderItem.createdAt.goe(start), orderItem.createdAt.loe(end));
    }
}
