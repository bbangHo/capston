package com.example.capstone.seller.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepositoryCustom {
    private final JPAQueryFactory queryFactory;


}
