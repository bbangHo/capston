package com.example.capstone.inquiry.service;

import com.example.capstone.common.BaseEntity;
import com.example.capstone.inquiry.Inquiry;
import com.example.capstone.inquiry.common.InquiryStatus;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.inquiry.repository.InquiryRepository;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.seller.Seller;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.doReturn;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class InquiryServiceTest {

    @InjectMocks
    private InquiryService inquiryService;

    @Mock
    private InquiryRepository inquiryRepository;

    @Mock
    private Item item;

    @Mock
    private Member member;

    @Mock
    private Seller seller;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void searchInquiry() {


        //given
        doReturn(getItem()).when(itemRepository).findById(1L);
        doReturn(getInquiryList()).when(inquiryRepository).findByItemId(getItem().orElseThrow().getId(), PageRequest.of(0,6, Sort.by("createdAt").descending()));


        //when
        final List<InquiryResponseDTO.Inquiry> inquiries = inquiryService.searchInquiries(1L,0,6).getInquiryList();

        //then
        Assertions.assertThat(inquiries.size()).isEqualTo(5);
        LocalDateTime preDate = inquiries.get(0).getCreatedAt();
        for (int i =1; i<5; i++){
            Assertions.assertThat(inquiries.get(i).getCreatedAt()).isAfter(preDate);
            preDate = inquiries.get(i).getCreatedAt();
        }
    }

    private Page<Inquiry> getInquiryList() {
        List<Inquiry> inquiryList = new ArrayList<>();

        for (int i = 0; i< 5; i++) {


            Inquiry inquiry = Inquiry.builder()
                    .answer("testAnswer"+i)
                    .content("testContent"+i)
                    .fromMember(member)
                    .toMember(seller)
                    .item(item)
                    .status(InquiryStatus.COMPLETE)
                    .build();

            ReflectionTestUtils.setField(inquiry, BaseEntity.class,"createdAt", LocalDateTime.now(), LocalDateTime.class);
            inquiryList.add(inquiry);

        }
        return new PageImpl<>(inquiryList);
    }

    private Optional<Item> getItem(){
        Item item = Item.builder()
                .id(1L)
                .build();

        return Optional.of(item);
    }


}



