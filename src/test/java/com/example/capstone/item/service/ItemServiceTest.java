package com.example.capstone.item.service;

import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.common.ItemType;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    // TODO : 리팩토링 필요

    void setUp() {
        category1 = categoryRepository.saveAndFlush(new Category(1L, "과일"));
        Category category2 = categoryRepository.saveAndFlush(new Category(2L, "야채"));

        for (int i = 0; i < 10; i++) {
            Item item = itemRepository.saveAndFlush(new Item((long) i, null, category1, ItemType.COMMON,
                    "item" + i, "item 설명", 10000 + i, 3000 + i, 10 + i,
                    "url", null, null, null));
        }

        for (int i = 10; i < 20; i++) {
            Item item = itemRepository.saveAndFlush(new Item((long) i, null, category2, ItemType.COMMON,
                    "item" + i, "item 설명", 10000 + i, 3000 + i, 10 + i,
                    "url", null, null, null));
        }
    }

    static Category category1;
    @Test
    @DisplayName("카테고리 별로 아이템 리스트를 가져온다.")
    void getItemListTest() {
        setUp();

        List<ItemResponseDTO.Item> itemList = itemService.getItemList(category1.getId(), 0, 10);

        for (ItemResponseDTO.Item item : itemList) {
            assertThat(item.getCategory()).isEqualTo(category1.getName());
        }
    }
}