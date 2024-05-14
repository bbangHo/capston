package com.example.capstone.groupPurchaseItem.repository;

import com.example.capstone.config.QueryDslConfig;
import com.example.capstone.item.GroupPurchaseItem;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.GroupPurchaseItemRepository;
import com.example.capstone.item.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest
@Import(QueryDslConfig.class)
public class GroupPurchaseItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private GroupPurchaseItemRepository groupPurchaseItemRepository;

    @DisplayName("특정 공동 구매 상품 조회")
    @Test
    void searchGroupPurchaseItem(){

        //given
        itemRepository.save(getItems().get(0));
        Item itemWithId = itemRepository.findAll().get(0);
        GroupPurchaseItem groupPurchaseItem = getGroupItem(itemWithId);
        groupPurchaseItemRepository.save(getGroupItem(itemWithId));
        GroupPurchaseItem groupPurchaseItemWithId = groupPurchaseItemRepository.findAll().get(0);


        //when
        Optional<GroupPurchaseItem> result = groupPurchaseItemRepository.findGroupPurchaseItemsById(groupPurchaseItemWithId.getId());

        //then
        assertThat(result.orElseThrow()).usingRecursiveComparison().ignoringFields("id", "createdAt","updatedAt").isEqualTo(groupPurchaseItem);
    }

    private List<Item> getItems(){
        List<Item> items = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Item item = Item.builder()
                    .name("name" + i)
                    .price(1000)
                    .deliveryCharge(3000)
                    .stock(200)
                    .itemDetailsImageUrl("URL")
                    .deadline(LocalDateTime.now().plusDays(1))
                    .build();
            items.add(item);
        }
        return items;
    }

    private GroupPurchaseItem getGroupItem(Item itemWithId){

        GroupPurchaseItem groupPurchaseItem = GroupPurchaseItem.builder()
                    .item(itemWithId)
                    .discountPrice(1000)
                    .targetQuantity(3)
                    .build();

        return groupPurchaseItem;
    }
    private List<GroupPurchaseItem> getGroupItem(List<Item> itemsWithId){
        List<GroupPurchaseItem> groupPurchaseItems = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            GroupPurchaseItem groupPurchaseItem = GroupPurchaseItem.builder()
                    .item(itemsWithId.get(i%5))
                    .discountPrice(i * 1000)
                    .targetQuantity(i * 3)
                    .build();
        }
        return groupPurchaseItems;
    }


}
