package com.example.capstone.inquiry.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.inquiry.Inquiry;
import com.example.capstone.inquiry.common.InquiryStatus;
import com.example.capstone.inquiry.converter.InquiryConverter;
import com.example.capstone.inquiry.dto.InquiryRequestDTO;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.inquiry.repository.InquiryRepository;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
import com.example.capstone.member.Member;
import com.example.capstone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.capstone.apiPayload.code.status.ErrorStatus.ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InquiryServiceImpl implements InquiryService{

    private final InquiryRepository inquiryRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;



    @Override
    public InquiryResponseDTO.InquiryList searchInquiries(Long itemId, Integer page, Integer size){

        Long validatedItemId = validateItem(itemId).getId();

        Page<Inquiry> inquiryPage = inquiryRepository.findByItemId(validatedItemId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return InquiryConverter.toInquiryList(inquiryPage);
    }

    @Override
    public void addInquiry(InquiryRequestDTO.requestedInquiry inquiry, Long memberId){

        Item item = itemRepository.findById(inquiry.getItemId()).orElseThrow(() -> new ExceptionHandler(ITEM_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Inquiry result = Inquiry.builder()
                .fromMember(member)
                .toMember(item.getSeller())
                .item(item)
                .content(inquiry.getContent())
                .status(InquiryStatus.WAIT)
                .build();

        inquiryRepository.save(result);
    }

    private Item validateItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ExceptionHandler(ITEM_NOT_FOUND));
    }
}
