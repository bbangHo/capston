package com.example.capstone.inquiry.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.inquiry.dto.InquiryRequestDTO;
import com.example.capstone.inquiry.dto.InquiryResponseDTO;
import com.example.capstone.inquiry.service.InquiryService;
import com.example.capstone.order.dto.CartRequestDTO;
import com.example.capstone.security.dto.MemberSecurityDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping("/inquiry")
    public ApiResponse<InquiryResponseDTO.InquiryList> getItemList(@Valid @Positive @RequestParam(name = "itemId") Long itemId,
                                                                   @Min(1) @RequestParam(name = "page") Integer page,
                                                                   @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(inquiryService.searchInquiries( itemId, page - 1, size));
    }

    @PostMapping(value = "/auth/inquiry",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> addInquiry(@AuthenticationPrincipal MemberSecurityDTO member,
                                          @Valid @RequestBody InquiryRequestDTO.requestedInquiry inquiry,
                                          BindingResult bindingResult) {

        log.info("Add Inquiry start.........");

        if(bindingResult.hasErrors()) {
            log.info("bindException...............");
        }

        inquiryService.addInquiry(inquiry, member.getId());

        log.info("Add Inquiry success.........");

        return ApiResponse.of(SuccessStatus._OK_ADD_INQUIRY, null);
    }

}
