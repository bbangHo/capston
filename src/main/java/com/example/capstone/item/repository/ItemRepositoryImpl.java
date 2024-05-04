package com.example.capstone.item.repository;

import com.example.capstone.item.Item;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.capstone.item.QItem.item;
import static com.example.capstone.order.QOrderItem.orderItem;

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
}
