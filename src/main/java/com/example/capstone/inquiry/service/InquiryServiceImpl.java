package com.example.capstone.inquiry.service;

import com.example.capstone.exception.handler.ErrorHandler;
import com.example.capstone.inquiry.Inquiry;
import com.example.capstone.inquiry.converter.InquiryConverter;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.inquiry.repository.InquiryRepository;
import com.example.capstone.item.Item;
import com.example.capstone.item.repository.ItemRepository;
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


    public InquiryResponseDTO.InquiryList searchInquiries(Long itemId, Integer page, Integer size){

        Long validatedItemId = validateItem(itemId).getId();

        Page<Inquiry> inquiryPage = inquiryRepository.findByItemId(validatedItemId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return InquiryConverter.toInquiryList(inquiryPage);
    }

    private Item validateItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ErrorHandler(ITEM_NOT_FOUND));
    }
}
