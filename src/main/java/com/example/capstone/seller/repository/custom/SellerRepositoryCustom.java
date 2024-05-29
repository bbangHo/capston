package com.example.capstone.seller.repository.custom;

import com.example.capstone.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerRepositoryCustom {
    Page<Item> getImminentItem(Long sellerId, Pageable pageable);
}
