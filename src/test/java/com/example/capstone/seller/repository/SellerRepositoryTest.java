package com.example.capstone.seller.repository;


import com.example.capstone.config.QueryDslConfig;
import com.example.capstone.member.Member;
import com.example.capstone.member.Subscription;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.member.repository.SubscriptionRepository;
import com.example.capstone.seller.Seller;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
@Import(QueryDslConfig.class)
public class SellerRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @DisplayName("구독한 판매자 조회")
    @Test
    void searchSubscribedSeller(){

        //given

        List<Seller> sellers = getSeller();
        List<Seller> storedSellers = sellerRepository.saveAllAndFlush(sellers);

        List<Member> members = getMembers();
        List<Member> storedMembers = memberRepository.saveAllAndFlush(members);

        List<Subscription> subscriptions = getsubscription(storedSellers, storedMembers);
        List<Subscription> storedSubscriptions = subscriptionRepository.saveAllAndFlush(subscriptions);

        // when
        Page<Seller> sellerList = sellerRepository.findSubscribedSeller(storedMembers.get(0).getId(), PageRequest.of(0, 100, Sort.by("createdAt").ascending()));
        Page<Seller> restrictedSellerList = sellerRepository.findSubscribedSeller(storedMembers.get(0).getId(), PageRequest.of(0, 5, Sort.by("createdAt").descending()));

        // then
        assertThat(sellerList.getContent().size()).isEqualTo(19);
        assertThat(sellerList.getContent().get(0).getCreatedAt().isAfter(sellerList.getContent().get(1).getCreatedAt()));
        assertThat(restrictedSellerList.getContent().size()).isEqualTo(5);
    }



    private List<Member> getMembers(){
        List<Member> members = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            Member member = Member.builder().build();
            members.add(member);
        }


        return members;
    }

    private List<Seller> getSeller(){
        List<Seller> sellers = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            Seller seller = Seller.builder().build();
            sellers.add(seller);
        }

        return sellers;
    }

    private List<Subscription> getsubscription(List<Seller> sellers, List<Member> members){

        List<Subscription> subscriptions = new ArrayList<>();

        for (int i = 1; i < 20; i++) {

            Subscription subscription = Subscription.builder()
                    .fromMember(members.get(0))
                    .toMember(sellers.get(i-1))
                    .build();
            subscriptions.add(subscription);
        }

        return subscriptions;
    }



}

