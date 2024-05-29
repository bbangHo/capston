package com.example.capstone.item.repository.custom;

import com.example.capstone.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * QueryDSL 로 커스텀해서 사용할 메소드 선언하는 인터페이스
 */
@Repository
public interface ItemRepositoryCustom {
    List<Item> findImminentItem();
    Page<Item> findImminentItem(Pageable pageable);
}
