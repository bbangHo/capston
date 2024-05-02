package com.example.capstone.item.repository;

import com.example.capstone.item.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void searchPopularItem(){
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        Page<Item> result = itemRepository.searchPopularItem(today, today.plusDays(1),PageRequest.of(0,6, Sort.by("createdAt").descending()));

        for (Item res : result) {
            System.out.println(res.getId());
        }
    }

    @Test
    void searchSubscribedItem(){

        Page<Item> result = itemRepository.searchSubscribedItem(2L, PageRequest.of(0,6));

        for (Item res : result) {
            System.out.println("여기요!!!!!!!!!!!!!!!!!"+res.getId());
        }
    }

    @Test
    void searchAllItem(){

        Page<Item> result = itemRepository.findAllBy(PageRequest.of(0,6,Sort.by("createdAt").descending()));

        for (Item res : result) {
            System.out.println("여기요!!!!!!!!!!!!!!!!!"+res.getId());
        }
    }
}
