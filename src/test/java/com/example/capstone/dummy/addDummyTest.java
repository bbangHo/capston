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
import org.hibernate.annotations.BatchSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;


@SpringBootTest
public class addDummyTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @BatchSize(size = 1000)
    void addDummyData(){
        for (int i = 1; i < 501; i++) {

            Category category = Category.builder()
                    .id((long)i)
                    .name("category" + i)
                    .build();
            categoryRepository.saveAndFlush(category);

            GroupPurchaseItem groupPurchaseItem = GroupPurchaseItem.builder()
                    .id((long)i)
                    .targetQuantity(100)
                    .discountPrice(1000)
                    .build();

            groupPurchaseItemRepository.saveAndFlush(groupPurchaseItem);


            Member member = Member.builder()
                    .id((long)i)
                    .loginId("loginId"+i)
                    .password(passwordEncoder.encode("password" + i))
                    .name("name"+i)
                    .nickName("nickname"+i)
                    .phone("phone"+i)
                    .type(MemberType.ROLE_CONSUMER)
                    .status(MemberStatus.ACTIVITY)
                    .build();
            memberRepository.saveAndFlush(member);



            Order order = Order.builder()
                    .id((long)i)
                    .member(member)
                    .build();

            orderRepository.saveAndFlush(order);





            Seller seller = Seller.builder()
                    .id((long)i)
                    .details("detail"+i)
                    .imageUrl("imageURL"+i)
                    .introduction("introduction"+i)
                    .build();

            sellerRepository.saveAndFlush(seller);

            if((i%2) ==0){
                member.addSeller(seller);
            }

            memberRepository.saveAndFlush(member);

            Item item = Item.builder()
                    .id((long)i)
                    .category(category)
                    .groupPurchaseItem(groupPurchaseItem)
                    .name("item"+(i+500))
                    .price(1000)
                    .deliveryCharge(4000)
                    .stock(100)
                    .itemDetailsImageUrl("imageURL")
                    .deadline(LocalDateTime.parse("2024-12-31T00:00:00.000000"))
                    .seller(seller)
                    .build();

            itemRepository.saveAndFlush(item);


            OrderItem orderItem = OrderItem.builder()
                    .id((long)i)
                    .order(order)
                    .item(item)
                    .quantity(i)
                    .status(OrderStatus.SHIPPING)
                    .build();

            orderItemRepository.saveAndFlush(orderItem);


            Subscription subscription = Subscription.builder()
                    .id((long) i)
                    .fromMember(member)
                    .toMember(seller)
                    .build();

            subscriptionRepository.saveAndFlush(subscription);

            Cart cart = Cart.builder()
                    .id((long)i)
                    .item(item)
                    .member(member)
                    .quantity((i%4)+1)
                    .build();

            cartRepository.saveAndFlush(cart);

            Alarm alarm = Alarm.builder()
                    .id((long)i)
                    .isConfirmed(false)
                    .content("AlarmContent" + i)
                    .title("AlarmTitle" + i)
                    .member(member)
                    .build();

            alarmRepository.saveAndFlush(alarm);

        }
    }

    @DisplayName("비밀번호 암호화")
    @Test
    void testStockQuantity(){
        for (int i = 1; i < 30; i++) {

            Member member = Member.builder()
                    .id((long) i)
                    .loginId("loginId" + i)
                    .password(passwordEncoder.encode("password" + i))
                    .name("name" + i)
                    .nickName("nickname" + i)
                    .phone("phone" + i)
                    .type(MemberType.ROLE_CONSUMER)
                    .status(MemberStatus.ACTIVITY)
                    .build();
            memberRepository.saveAndFlush(member);

        }
    }

}
