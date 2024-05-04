package com.example.capstone.item.service;

import com.example.capstone.exception.GeneralException;
import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.common.ItemType;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;


    void setUp() {
        category1 = categoryRepository.save(new Category(1L, "과일"));
        category2 = categoryRepository.save(new Category(2L, "야채"));

        for (int i = 0; i < 10; i++) {
            Item item = itemRepository.save(Item.builder()
                    .category(category1)
                    .type(ItemType.COMMON)
                    .name("상품" + i)
                    .price(i + 10000)
                    .deliveryCharge(i + 1000)
                    .stock(i)
                    .deadline(LocalDateTime.parse("2024-12-31T00:00:00.000000"))
                    .ItemDetailsImageUrl("img")
                    .build());
        }

        for (int i = 10; i < 20; i++) {
            Item item = itemRepository.save(Item.builder()
                    .category(category2)
                    .type(ItemType.COMMON)
                    .name("상품" + i)
                    .price(i + 10000)
                    .deliveryCharge(i + 1000)
                    .stock(i)
                    .deadline(LocalDateTime.parse("2024-12-31T00:00:00.000000"))
                    .ItemDetailsImageUrl("img")
                    .build());
        }
    }

    static Category category1;
    static Category category2;

    @Test
    @DisplayName("성공 테스트 - 카테고리 별로 아이템 리스트를 가져온다.")
    @Transactional
    void getItemListTest() {
        setUp();

        ItemResponseDTO.ItemList itemList = itemService.getItemList(category1.getId(), 0, 10);

        for (ItemResponseDTO.Item item : itemList.getItemList()) {
            assertThat(item.getCategory()).isEqualTo(category1.getName());
        }
    }

    @Test
    @DisplayName("없는 카테고리를 요청하면 실패한다.")
    @Transactional
    void getItemListFailureTest() {
        setUp();

        assertThrows(GeneralException.class,
                () -> itemService.getItemList(category1.getId() + category2.getId(), 0, 10));
    }

    @Test
    @DisplayName("QueryDsl Test - 마감 임박 상품 가져오기 테스트")
    @Transactional
    void QueryDslTest() {
        LocalDateTime imminentDate = LocalDateTime.now().plusDays(7);
        Pageable pageable = PageRequest.of(0, 6, Sort.by("createdAt").ascending());

        List<Item> dslResult = itemRepository.findImminentItem();
        Page<Item> jpqlResult = itemRepository.searchImminentItem(imminentDate, pageable);

        List<ItemResponseDTO.Item> itemList = jpqlResult.stream()
                .map(ItemConverter::toItemResponseDTO)
                .toList();

        for (int i = 0; i < jpqlResult.getSize(); i++) {
            Item dslItem = dslResult.get(i);
            ItemResponseDTO.Item jpqlItem = itemList.get(i);

//            System.out.println("getId = " + dslItem.getId() + " " + jpqlItem.getId());
//            System.out.println("getName = " + dslItem.getName() + " " + jpqlItem.getName());
//            System.out.println("getDeadline = " + dslItem.getDeadline() + " " + jpqlItem.getDeadline());
//            System.out.println("getPrice = " + dslItem.getPrice() + " " + jpqlItem.getPrice());
//            System.out.println("getStock = " + dslItem.getStock() + " " + jpqlItem.getStock());
//            System.out.println("getCategory = " + dslItem.getCategory() + " " + jpqlItem.getCategory());
//            System.out.println();

            assertThat(dslItem.getId()).isEqualTo(jpqlItem.getId());
            assertThat(dslItem.getName()).isEqualTo(jpqlItem.getName());
        }

    }
}