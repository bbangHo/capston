package com.example.capstone.inquiry.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.inquiry.service.InquiryService;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.service.ItemQueryService;
import com.example.capstone.item.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping
    public ApiResponse<InquiryResponseDTO.InquiryList> getItemList(@Valid @Positive @RequestParam(name = "itemId") Long itemId,
                                                                   @Min(1) @RequestParam(name = "page") Integer page,
                                                                   @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(inquiryService.searchInquiries( itemId, page - 1, size));
    }

}
