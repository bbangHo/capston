package com.example.capstone.member.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.member.dto.MemberRequestDTO;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.member.service.MemberServiceImpl;
import com.example.capstone.security.dto.MemberSecurityDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.capstone.member.common.MemberValidator.validateLoginId;
import static com.example.capstone.member.common.MemberValidator.validateNickName;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberServiceImpl memberService;

    @PatchMapping("/auth/member/to-seller")
    public ApiResponse<MemberResponseDTO.MemberState> changeMemberRole(@AuthenticationPrincipal MemberSecurityDTO member) {
        Long memberId = member.getId();
        MemberResponseDTO.MemberState response = memberService.changeMemberRole(memberId);
        return ApiResponse.of(SuccessStatus._OK_CHANGE_ROLE, response);
    }

    @PostMapping("/member/dupCheck")
    public ApiResponse<MemberResponseDTO.DupCheckField> dupCheck(@Valid @RequestBody MemberRequestDTO.DupCheckField dupCheckFields) {

            log.info("dupCheck controller start ...............");


        if(!dupCheckFields.getType().equals("loginId") && !dupCheckFields.getType().equals("nickName") ){

            throw new ExceptionHandler(ErrorStatus.DUP_CHECK_FIELD_BADTYPE);
        }

        validateMember(dupCheckFields);

        MemberResponseDTO.DupCheckField dupCheckField = memberService.checkField(dupCheckFields);

        log.info("dupCheck success...............");

        return ApiResponse.of(SuccessStatus._OK_DUP_CHECK,dupCheckField);

    }

    @DeleteMapping("/member/{memberId}")
    public ApiResponse<String> deleteTempMember(@PathVariable Long memberId) {

        log.info("deleteTempMember controller start ...............");

        memberService.deleteTempMember(memberId);

        log.info("deleteTempMember success...............");

        return ApiResponse.of(SuccessStatus._OK_DELETE_TEMP_MEMBER,null);

    }

    @PostMapping("/member/signUp")
    public ApiResponse<MemberResponseDTO.SignUpMember> signUp(@Valid @RequestBody MemberRequestDTO.SignUpMember signUpMember) {

        log.info("signUp controller start ...............");

        MemberResponseDTO.SignUpMember member = memberService.signUp(signUpMember);


        log.info("signUp success...............");

        return ApiResponse.of(SuccessStatus._OK_SIGNUP,member);

    }

    void validateMember(MemberRequestDTO.DupCheckField dupCheckFields){
        if (!dupCheckFields.getLoginId().isEmpty() && !validateLoginId(dupCheckFields.getLoginId()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_LODINID);

        if(!dupCheckFields.getNickName().isEmpty() && !validateNickName(dupCheckFields.getNickName()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_NICKNAME);
    }

}
