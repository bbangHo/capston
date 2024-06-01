package com.example.capstone.order.repository;

import com.example.capstone.order.OrderItem;
import com.example.capstone.order.repository.custom.OrderItemRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {

    List<OrderItem> findByOrderId(Long orderId);

    @Query("select oi from OrderItem oi where oi.order.member.id = :memberId")
    Page<OrderItem> searchOrdersByMember(Long memberId, Pageable pageable);

    @Query("select oi from OrderItem oi where oi.order.member.id = :memberId and oi.item.groupPurchaseItem is not null ")
    Page<OrderItem> searchGroupOrderSum(Long memberId, Pageable pageable);

    @Query("select sum(oi.quantity) from OrderItem oi where oi.item.id = :itemId and oi.status != 'CANCELED'")
    Integer searchGroupOrderSum(Long itemId);

}
