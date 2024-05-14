package com.example.capstone.order.repository;

import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.order.Order;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.common.DateType;
import com.example.capstone.order.common.OrderStatus;
import com.example.capstone.seller.Seller;
import com.example.capstone.seller.repository.SellerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    static Member member;
    static Seller seller;
    static Member consumer;
    static Item item;
    static Order order;
    static OrderItem orderItem;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(
                Member.builder()
                        .name("seller")
                        .build()
        );

        seller = sellerRepository.save(
                Seller.builder()
                        .member(member)
                        .build()
        );

        item = itemRepository.save(
                Item.builder()
                        .name("test-item")
                        .price(10000)
                        .deliveryCharge(1000)
                        .stock(100)
                        .deadline(LocalDateTime.of(2024, 12, 31, 0, 0, 0))
                        .seller(seller)
                        .itemDetailsImageUrl("null")
                        .build()
        );

        consumer = memberRepository.save(
                Member.builder()
                        .name("consumer")
                        .build()
        );

        order = orderRepository.save(
                Order.builder()
                        .member(consumer)
                        .status(OrderStatus.PENDING)
                        .build()
        );

        orderItem = orderItemRepository.save(
                OrderItem.builder()
                        .item(item)
                        .order(order)
                        .quantity(10)
                        .build()
        );
    }

    @Test
    void 판매자_관리_페이지에서_판매자의_상품_목록을_가져온다() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<Item> itemList = itemRepository.findBySellerId(seller.getId(), pageable).getContent();

        // then
        for (Item i : itemList) {
            Assertions.assertThat(i).isEqualTo(item);
        }
    }

    @Test
    void 판매자의_상품_판매량_테스트() {
        List<Integer> salesVolume = orderItemRepository.getSalesVolume(seller.getId(), DateType.DAY);

//        //TODO: 값 넘어오는 거만 확인 했고 나중에 수정!
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        for (Integer i : salesVolume) {
//            System.out.println(i);
//        }
    }
}