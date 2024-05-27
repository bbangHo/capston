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
import jakarta.validation.constraints.NotNull;
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

    @DeleteMapping("/tempMember/loginId/{loginId}")
    public ApiResponse<String> deleteTempMemberWithLoginId(@PathVariable String loginId) {

        log.info("deleteTempMemberWithLoginId controller start ...............");

        memberService.deleteTempMemberWithLoginId(loginId);

        log.info("deleteTempMemberWithLoginId success...............");

        return ApiResponse.of(SuccessStatus._OK_DELETE_TEMP_MEMBER,null);

    }

    @DeleteMapping("/tempMember/nickName/{nickName}")
    public ApiResponse<String> deleteTempMemberWithNickName(@PathVariable String nickName) {

        log.info("deleteTempMemberWithNickName controller start ...............");

        memberService.deleteTempMemberWithNickName(nickName);

        log.info("deleteTempMemberWithNickName success...............");

        return ApiResponse.of(SuccessStatus._OK_DELETE_TEMP_MEMBER,null);

    }

    @PatchMapping("/member/signUp")
    public ApiResponse<MemberResponseDTO.SignUpMember> signUp(@Valid @RequestBody MemberRequestDTO.SignUpMember signUpMember) {

        log.info("signUp controller start ...............");

        MemberResponseDTO.SignUpMember member = memberService.signUp(signUpMember);


        log.info("signUp success...............");

        return ApiResponse.of(SuccessStatus._OK_SIGNUP,member);

    }

    @PatchMapping("/auth/member/nickName")
    public ApiResponse<String> modifyAuthMemberNickName(@RequestBody @NotNull String nickName) {

        log.info("modifyAuthMemberNickName controller start ...............");

        if(!validateNickName(nickName))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_NICKNAME);

        memberService.changeNickName(nickName);

        log.info("modifyAuthMemberNickName success...............");

        return ApiResponse.of(SuccessStatus._OK_DUP_CHECK,null);

    }

    void validateMember(MemberRequestDTO.DupCheckField dupCheckFields){
        if (!dupCheckFields.getLoginId().isEmpty() && !validateLoginId(dupCheckFields.getLoginId()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_LODINID);

        if(!dupCheckFields.getNickName().isEmpty() && !validateNickName(dupCheckFields.getNickName()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_NICKNAME);
    }

}
