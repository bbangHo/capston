package com.example.capstone.item.repository;

import com.example.capstone.item.GroupPurchaseItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupPurchaseItemRepository extends JpaRepository<GroupPurchaseItem, Long> {

    @Query("select g from GroupPurchaseItem g order by g.createdAt DESC")
    Page<GroupPurchaseItem> searchGroupPurchaseItem(Pageable pageable);

    Page<GroupPurchaseItem> findGroupPurchaseItemsBy(Pageable pageable);

    Optional<GroupPurchaseItem> findGroupPurchaseItemsById(Long groupItemId);

    @Query("select sum(oi.quantity) from OrderItem oi where oi.item.groupPurchaseItem.id =:groupItemId")
    Integer searchOrderSum(Long groupItemId);
}
