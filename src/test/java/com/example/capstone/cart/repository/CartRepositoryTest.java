package com.example.capstone.cart.repository;


import com.example.capstone.config.QueryDslConfig;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.order.Cart;
import com.example.capstone.order.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(QueryDslConfig.class)
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;


    @DisplayName("cart 담기 테스트(총합과 특정 멤버의 수)")
    @Test
    void searchReviews() {

        //given
        List<Item> items = getItems();
        List<Item> storedItems = itemRepository.saveAllAndFlush(items);

        List<Member> members = getMembers();
        List<Member> storedMembers = memberRepository.saveAllAndFlush(members);
        Long firstMemberId = storedMembers.get(0).getId();
        Long itemInCartSum = storedMembers.stream().filter(member -> member.getId().equals(firstMemberId)).count();

        List<Cart> carts = getCarts(storedItems, storedMembers);


        // when
        cartRepository.saveAllAndFlush(carts);

        // then
        assertThat(cartRepository.findAll().size()).isEqualTo(5);
        assertThat(cartRepository.findAll().stream().filter(member -> member.getId().equals(firstMemberId)).count()).isEqualTo(itemInCartSum);

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

    private List<Member> getMembers(){
        List<Member> members = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
           Member member = Member.builder().build();
            members.add(member);
        }


        return members;
    }

    private List<Cart> getCarts(List<Item> items, List<Member> members){
        List<Cart> carts = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Cart cart = Cart.builder()
                    .item(items.get(i-1))
                    .member(members.get((i%3)))
                    .quantity(3)
                    .build();

            carts.add(cart);
        }

        return carts;
    }

}
