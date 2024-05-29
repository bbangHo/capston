package com.example.capstone.seller.repository.custom;

import com.example.capstone.item.Item;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.example.capstone.item.QItem.item;

@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Item> getImminentItem(Long sellerId, Pageable pageable) {
        List<Item> orderItemJPAQuery = queryFactory
                .select(item)
                .from(item)
                .where(item.seller.id.eq(sellerId))
                .orderBy(pageable.getSort())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(item.count())
                .from(item)
                .where(item.seller.id.eq(sellerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(orderItemJPAQuery, pageable, count::fetchOne);
    }

    private BooleanBuilder orderBy(Pageable pageable) {

        builder.and(item.deadline.desc());
    }
}
