package com.example.capstone.member.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.member.service.MemberService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @PatchMapping("/auth/member/to-seller")
    public ApiResponse<MemberResponseDTO.MemberState> changeMemberRole(@AuthenticationPrincipal MemberSecurityDTO member) {
        Long memberId = member.getId();
        MemberResponseDTO.MemberState response = memberService.changeMemberRole(memberId);
        return ApiResponse.of(SuccessStatus._OK_CHANGE_ROLE, response);
    }
}
