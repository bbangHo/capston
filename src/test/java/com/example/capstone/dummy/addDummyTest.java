package com.example.capstone.dummy;

import com.example.capstone.item.Category;
import com.example.capstone.item.GroupPurchaseItem;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.CategoryRepository;
import com.example.capstone.item.repository.GroupPurchaseItemRepository;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Alarm;
import com.example.capstone.member.Member;
import com.example.capstone.member.Subscription;
import com.example.capstone.member.common.MemberStatus;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.member.repository.AlarmRepository;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.member.repository.SubscriptionRepository;
import com.example.capstone.order.Cart;
import com.example.capstone.order.Order;
import com.example.capstone.order.OrderItem;
import com.example.capstone.order.common.OrderStatus;
import com.example.capstone.order.repository.CartRepository;
import com.example.capstone.order.repository.OrderItemRepository;
import com.example.capstone.order.repository.OrderRepository;
import com.example.capstone.seller.Seller;
import com.example.capstone.seller.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@Transactional
@Rollback(value = false)
@SpringBootTest
public class addDummyTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    GroupPurchaseItemRepository groupPurchaseItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AlarmRepository alarmRepository;

    @Test
    void addDummyData(){
        for (int i = 10000; i < 10000 + 300; i++) {
            Category category = Category.builder()
                    .id((long)i)
                    .name("category" + i)
                    .build();

            GroupPurchaseItem groupPurchaseItem = GroupPurchaseItem.builder()
                    .id((long)i)
                    .targetQuantity(100)
                    .discountPrice(1000)
                    .build();

            Member member = Member.builder()
                    .id((long)i)
                    .loginId("loginId"+i)
                    .password("password"+i)
                    .name("name"+i)
                    .nickName("nickname"+i)
                    .phone("phone"+i)
                    .type(MemberType.CONSUMER)
                    .status(MemberStatus.ACTIVITY)
                    .build();

            Seller seller = Seller.builder()
                    .id((long)i)
                    .details("detail"+i)
                    .imageUrl("imageURL"+i)
                    .introduction("introduction"+i)
                    .build();

            Item item = Item.builder()
                    .id((long)i)
                    .category(category)
                    .groupPurchaseItem(groupPurchaseItem)
                    .name("item"+(i+40))
                    .price(1000)
                    .deliveryCharge(4000)
                    .stock(100)
                    .itemDetailsImageUrl("imageURL")
                    .deadline(LocalDateTime.parse("2024-12-31T00:00:00.000000"))
                    .seller(seller)
                    .build();

            Order order = Order.builder()
                    .id((long)i)
                    .member(member)
                    .build();

            OrderItem orderItem = OrderItem.builder()
                    .id((long)i)
                    .order(order)
                    .item(item)
                    .quantity(i)
                    .status(OrderStatus.SHIPPING)
                    .build();

            Member member2 = Member.builder()
                    .id((long)(i+20))
                    .seller(seller)
                    .loginId("loginId"+(i+10))
                    .password("password"+(i+10))
                    .name("name"+(i+10))
                    .nickName("nickname"+(i+10))
                    .phone("phone"+(i+10))
                    .type(MemberType.SELLER)
                    .status(MemberStatus.ACTIVITY)
                    .build();

            Subscription subscription = Subscription.builder()
                    .id((long)(i+20))
                    .fromMember(member)
                    .toMember(seller)
                    .build();

            Cart cart = Cart.builder()
                    .id((long)i)
                    .item(item)
                    .member(member)
                    .quantity((i%3)+1)
                    .build();

            Alarm alarm = Alarm.builder()
                    .id((long)i)
                    .isConfirmed(false)
                    .content("AlarmContent" + i)
                    .title("AlarmTitle" + i)
                    .member(member)
                    .build();

            categoryRepository.saveAndFlush(category);
            groupPurchaseItemRepository.saveAndFlush(groupPurchaseItem);
            memberRepository.saveAndFlush(member);
            sellerRepository.saveAndFlush(seller);
            itemRepository.saveAndFlush(item);
            orderRepository.saveAndFlush(order);
            orderItemRepository.saveAndFlush(orderItem);
            memberRepository.saveAndFlush(member2);
            subscriptionRepository.saveAndFlush(subscription);
            cartRepository.saveAndFlush(cart);
            alarmRepository.saveAndFlush(alarm);

        }
    }

    @Test
    void testStockQuantity(){
        LocalDateTime today = LocalDateTime.now().plusDays(7);
        Page<Item> testList2 = itemRepository.searchImminentItem(today, PageRequest.of(0,6));

    }

}
