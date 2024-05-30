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


}
