package com.example.capstone.item.repository;

import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item>findByCategoryId(Long categoryId, Pageable pageable);

    Page<Item>findByNameContaining(String keyword, Pageable pageable);
}
