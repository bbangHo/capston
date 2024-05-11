package com.example.capstone.grouppurchaseitem.repository;

import com.example.capstone.item.GroupPurchaseItem;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.GroupPurchaseItemRepository;
import com.example.capstone.item.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
@SpringBootTest
public class groupPurchaseItemRepositoryTest {

    @Autowired
    GroupPurchaseItemRepository groupPurchaseItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void addDummy(){
        for (int i = 0; i < 10; i++) {

            GroupPurchaseItem groupPurchaseItem = GroupPurchaseItem.builder()
                    .id((long)i)
                    .targetQuantity(100)
                    .discountPrice(1000)
                    .build();

            Item item = Item.builder()
                    .id((long)i+40)
                    .groupPurchaseItem(groupPurchaseItem)
                    .name("item"+(i+40))
                    .price(1000)
                    .deliveryCharge(4000)
                    .stock(100)
                    .ItemDetailsImageUrl("imageURL")
                    .deadline(LocalDateTime.parse("2024-12-31T00:00:00.000000"))
                    .build();

            groupPurchaseItemRepository.saveAndFlush(groupPurchaseItem);

            itemRepository.saveAndFlush(item);

        }
    }

    @Test
    void searchGroupPurchaseItem(){
        Page<GroupPurchaseItem> result = groupPurchaseItemRepository.searchGroupPurchaseItem(PageRequest.of(0,6));

        Page<GroupPurchaseItem> result2 = groupPurchaseItemRepository.findGroupPurchaseItemsBy(PageRequest.of(0,6, Sort.by("createdAt").descending()));

        System.out.print("끝");
    }

}
