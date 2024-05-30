package com.example.capstone.seller.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.common.QueryService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import com.example.capstone.seller.dto.SellerResponseDTO;
import com.example.capstone.seller.dto.TempSellerResponseDTO;
import com.example.capstone.seller.service.SellerManagementService;
import com.example.capstone.seller.service.SellerService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/auth/subscribedSeller")
    public ApiResponse<TempSellerResponseDTO.SellerList> getSubscribedSeller(
            @AuthenticationPrincipal MemberSecurityDTO member,
            @Min(1) @RequestParam(name = "page") Integer page,
            @Positive @RequestParam(name = "size") Integer size) {

        log.info("SellerController's getSubscribedSeller start...........");
        return ApiResponse.onSuccess(sellerService.searchSubscribedSeller(member.getId(), page - 1, size));
    }

}
