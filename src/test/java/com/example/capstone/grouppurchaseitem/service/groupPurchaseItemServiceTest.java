package com.example.capstone.grouppurchaseitem.service;

import com.example.capstone.item.repository.GroupPurchaseItemRepository;
import com.example.capstone.item.service.GroupPurchaseItemService;
import com.example.capstone.item.service.GroupPurchaseItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class groupPurchaseItemServiceTest {

    @Autowired
    GroupPurchaseItemService groupPurchaseItemService;

    @Test
    public void groupItemSearch(){
        groupPurchaseItemService.getGroupItemList(0,6);
        System.out.println("test");
    }
}
