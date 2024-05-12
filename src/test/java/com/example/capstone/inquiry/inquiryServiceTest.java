package com.example.capstone.inquiry;

import com.example.capstone.inquiry.common.InquiryStatus;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.inquiry.repository.InquiryRepository;
import com.example.capstone.inquiry.service.InquiryService;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.seller.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Deque;
import java.util.LinkedList;

import static com.example.capstone.inquiry.converter.InquiryConverter.toInquiryResponseDTO;

@Slf4j
@SpringBootTest
public class inquiryServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    InquiryRepository inquiryRepository;
    @Autowired
    InquiryService inquiryService;

    @Test
    @Transactional
    public void searchInquiry() {
        Deque<InquiryResponseDTO.Inquiry> inqui = new LinkedList<>();


        for (int i = 1; i <= 5; i++) {


            Inquiry inquiry = Inquiry.builder()
                    .answer("testAnswer"+i)
                    .content("testContent"+i)
                    .fromMember(memberRepository.findById((long) i).orElse(null))
                    .toMember(sellerRepository.findById((long) i).orElse(null))
                    .item(itemRepository.findById(1L).orElse(null))
                    .status(InquiryStatus.COMPLETE)
                    .build();


            inquiryRepository.saveAndFlush(inquiry);
            inqui.addFirst(toInquiryResponseDTO(inquiry));
        }

        InquiryResponseDTO.InquiryList inquiryList = inquiryService.searchInquiries(1L,0, 6);

        for (int i = 0; i <= 4; i++) {
            Assertions.assertThat(inquiryList.getInquiryList().get(i)).usingRecursiveComparison().isEqualTo(inqui.poll());
        }
    }
}

