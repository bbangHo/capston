package com.example.capstone.item.repository.custom;

import com.example.capstone.item.Item;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.capstone.item.QItem.item;
import static com.example.capstone.order.QOrderItem.orderItem;
import static com.example.capstone.seller.QSeller.seller;
import static com.example.capstone.member.QMember.member;


/**
 * ItemRepositoryCustom에 정의한 메소드를 구현하는 클래스
 */
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final static LocalDateTime DEADLINE_TIME_IMMINENT = LocalDateTime.now().plusDays(7);
    private final static Integer NUMBER_ITEMS = 6;

    /**
     * 마감 임박 상품 가져오기
     * 재고의 10% or 남은 기간 일주일 이내, 등록 오래된 순으로 가져옴
     * 해당 메소드는 위 조건을 충족하는 상품 pageable.getPageSize() 개만 반환합니다.
     *
     * @return Page<Item> Object
     */
    @Override
    public Page<Item> findImminentItem(Pageable pageable) {
        List<Item> imminentItemList = getImminentItemCommonQuery()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(item.count())
                .from(item)
                .innerJoin(orderItem)
                .on(item.id.eq(orderItem.item.id))
                .groupBy(item)
                .having(
                        // (item.stock - orderItem.quantity.sum() <= item.stock * 0.1) OR deadline < now() + 7d
                        (item.stock.subtract(orderItem.quantity.sum()).loe(item.stock.multiply(0.1)))
                                .or(item.deadline.lt(DEADLINE_TIME_IMMINENT))
                );

        return PageableExecutionUtils.getPage(imminentItemList, pageable, count::fetchOne);
    }

    /**
     * 마감 임박 상품 가져오기
     * 재고의 10% or 남은 기간 일주일 이내, 등록 오래된 순으로 가져옴
     * 해당 메소드는 위 조건을 충족하는 상품 6개만 반환합니다.
     */
    @Override
    public List<Item> findImminentItem() {
        return getImminentItemCommonQuery()
                .limit(NUMBER_ITEMS)
                .fetch();
    }

    public JPAQuery<Item> getImminentItemCommonQuery() {
        return queryFactory
                .select(item)
                .from(item)
                .innerJoin(orderItem)
                .on(item.id.eq(orderItem.item.id))
                .groupBy(item)
                .having(
                        // (item.stock - orderItem.quantity.sum() <= item.stock * 0.1) OR deadline < now() + 7d
                        (item.stock.subtract(orderItem.quantity.sum()).loe(item.stock.multiply(0.1)))
                                .or(item.deadline.lt(DEADLINE_TIME_IMMINENT))
                )
                .orderBy(item.createdAt.asc());
    }

    /**
     * 판매자가 올린 물품 중 마감이 임박한 상품을 보여줍니다. 정렬이 가능합니다.
     * 마감 임박 상품 - 재고가 10% 남았거나, 마감일이 7일 이내인 상품
     *
     * @param sellerId - 판매자 pk
     * @param pageable
     * @param sort
     * @param order
     * @return
     */
    @Override
    public Page<Item> getImminentItem(Long sellerId, Pageable pageable, String sort, String order) {
        OrderSpecifier<?>[] orderCondition = createOrderSpecifier(sort, order);

        List<Item> orderItemJPAQuery = queryFactory
                .select(item)
                .from(item)
                .join(orderItem).on(item.id.eq(orderItem.item.id))
//                .join(seller).on(item.seller.id.eq(seller.id))
//                .join(member).on(member.seller.id.eq(seller.id))
                .where(item.seller.member.id.eq(sellerId))
//                .groupBy(orderItem)
//                .having(
                        // (item.stock - orderItem.quantity.sum() <= item.stock * 0.1) OR deadline < now() + 7d
//                        (item.stock.subtract(orderItem.quantity.sum()).loe(item.stock.multiply(0.1)))
//                                .or(item.deadline.lt(DEADLINE_TIME_IMMINENT))
//                )
                .orderBy(orderCondition)
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(item.count())
                .from(item)
                .join(orderItem)
                .on(item.id.eq(orderItem.item.id))
                .where(item.seller.member.id.eq(sellerId))
                .groupBy(orderItem)
                .having(
                        // (item.stock - orderItem.quantity.sum() <= item.stock * 0.1) OR deadline < now() + 7d
                        (item.stock.subtract(orderItem.quantity.sum()).loe(item.stock.multiply(0.1)))
                                .or(item.deadline.lt(DEADLINE_TIME_IMMINENT))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(orderItemJPAQuery, pageable, count::fetchOne);
    }

    private OrderSpecifier<?>[] createOrderSpecifier(String sortBy, String orderBy) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        Order order = orderBy.equalsIgnoreCase("DESC") ? Order.DESC : Order.ASC;

        if (sortBy.equalsIgnoreCase("name")) {
            orderSpecifiers.add(new OrderSpecifier<>(order, item.name));
        }

        if (sortBy.equalsIgnoreCase("stock")) {
            orderSpecifiers.add(new OrderSpecifier<>(order, item.stock));
        }

        if (sortBy.equalsIgnoreCase("deadline")) {
            orderSpecifiers.add(new OrderSpecifier<>(order, item.deadline));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

//    private Path<?> createOrderSpecifier(String sortBy, String orderBy) {
//        Order order = orderBy.equalsIgnoreCase("DESC") ? Order.DESC : Order.ASC;
//
//        if (sortBy.equalsIgnoreCase("name")) {
//            return Expressions.path(String.class, "name");
//        }
//
//        if (sortBy.equalsIgnoreCase("stock")) {
//            return Expressions.numberPath(Long.class, "stock");
//        }
//        return Expressions.datePath(LocalDateTime.class, "name");
//    }
}