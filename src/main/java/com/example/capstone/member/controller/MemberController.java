package com.example.capstone.member.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.apiPayload.code.status.SuccessStatus;
import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.item.dto.ItemResponseDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.example.capstone.member.common.MemberValidator.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberServiceImpl memberService;

    @GetMapping("/auth/member/order")
    public ApiResponse<List<ItemResponseDTO.ItemStatusList>> getOrderedItemStatus(@AuthenticationPrincipal MemberSecurityDTO member) {
        List<ItemResponseDTO.ItemStatusList> response = memberService.getOrderedItemStatus(member.getId());
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/auth/member/to-seller")
    public ApiResponse<MemberResponseDTO.MemberState> changeMemberRole(@AuthenticationPrincipal MemberSecurityDTO member,
                                                                       @RequestPart(required = false) MultipartFile multipartFile,
                                                                       @RequestPart MemberRequestDTO.ToSeller request) {
        Long memberId = member.getId();
        MemberResponseDTO.MemberState response = memberService.changeMemberRole(memberId, request, multipartFile);
        return ApiResponse.of(SuccessStatus._OK_CHANGE_ROLE, response);
    }

    @PostMapping("/member/dupCheck")
    public ApiResponse<MemberResponseDTO.DupCheckField> dupCheck(@Valid @RequestBody MemberRequestDTO.DupCheckField dupCheckFields) {

            log.info("dupCheck controller start ...............");


        if(!dupCheckFields.getType().equals("loginId") && !dupCheckFields.getType().equals("nickName") ){

            throw new ExceptionHandler(ErrorStatus.DUP_CHECK_FIELD_BADTYPE);
        }

        validateUniqueMember(dupCheckFields);

        MemberResponseDTO.DupCheckField dupCheckField = memberService.checkField(dupCheckFields);

        log.info("dupCheck success...............");

        return ApiResponse.of(SuccessStatus._OK_DUP_CHECK,dupCheckField);

    }

    @DeleteMapping("/tempMember/{id}")
    public ApiResponse<String> deleteTempMemberWithLoginId(@PathVariable Long id) {

        log.info("deleteTempMember controller start ...............");

        memberService.deleteTempMember(id);

        log.info("deleteTempMember success...............");

        return ApiResponse.of(SuccessStatus._OK_DELETE_TEMP_MEMBER, null);

    }

    @PatchMapping("/member/signUp")
    public ApiResponse<MemberResponseDTO.SignUpMember> signUp(@Valid @RequestBody MemberRequestDTO.SignUpMember signUpMember) {

        log.info("signUp controller start ...............");

        validateSignUpMember(signUpMember);

        MemberResponseDTO.SignUpMember member = memberService.signUp(signUpMember);


        log.info("signUp success...............");

        return ApiResponse.of(SuccessStatus._OK_SIGNUP,member);

    }

    @PatchMapping("/auth/member/nickName")
    public ApiResponse<MemberResponseDTO.DupCheckField> modifyAuthMemberNickName(@AuthenticationPrincipal MemberSecurityDTO member, @RequestBody @NotNull Map<String, String> nickName) {

        log.info("modifyAuthMemberNickName controller start ...............");

        if(!validateNickName(nickName.get("nickName")))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_NICKNAME);

        MemberRequestDTO.DupCheckField dupCheckField = MemberRequestDTO.DupCheckField.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .type("nickName")
                .nickName(nickName.get("nickName"))
                .build();

        MemberResponseDTO.DupCheckField result = memberService.checkField(dupCheckField);

        log.info("modifyAuthMemberNickName success...............");

        return ApiResponse.of(SuccessStatus._OK_CHANGE_NICKNAME, result);

    }

    @GetMapping("/auth/member")
    public ApiResponse<MemberRequestDTO.ChangeableMemberData> getAuthMemberDATA(@AuthenticationPrincipal MemberSecurityDTO member) {

        log.info("getAuthMemberDATA controller start ...............");

        MemberRequestDTO.ChangeableMemberData result = memberService.getMemberData(member.getId());

        log.info("getAuthMemberDATA success...............");

        return ApiResponse.onSuccess(result);
    }

    @PatchMapping("/auth/member")
    public ApiResponse<MemberRequestDTO.ChangeableMemberData> modifyAuthMember(@AuthenticationPrincipal MemberSecurityDTO member, @RequestBody @NotNull MemberRequestDTO.ChangeableMemberData changeableMemberData) {

        log.info("modifyAuthMember controller start ...............");

        validateMember(changeableMemberData);

        MemberRequestDTO.ChangeableMemberData result = memberService.changeMemberData(member.getId(), changeableMemberData);

        log.info("modifyAuthMember success...............");

        return ApiResponse.of(SuccessStatus._OK_CHANGE_MEMBER_DATA, result);
    }



    void validateUniqueMember(MemberRequestDTO.DupCheckField dupCheckFields){
        if (!(dupCheckFields.getLoginId() == null) && !validateLoginId(dupCheckFields.getLoginId()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_LOGINID);

        if(!(dupCheckFields.getNickName() == null) && !validateNickName(dupCheckFields.getNickName()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_NICKNAME);
    }

    void validateSignUpMember(MemberRequestDTO.SignUpMember signUpMember){
        if ((signUpMember.getName() == null ) || !validateName(signUpMember.getName()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_NAME);
        if ((signUpMember.getPhone() == null ) || !validatePhone(signUpMember.getPhone()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_PHONE);
        if ((signUpMember.getPassword() == null) || !validatePassword(signUpMember.getPassword()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_PASSWORD);
    }


    void validateMember(MemberRequestDTO.ChangeableMemberData changeableMemberData){
        if ((changeableMemberData.getPhone() == null ) || !validatePhone(changeableMemberData.getPhone()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_PHONE);

        if ((changeableMemberData.getPassword() == null) || !validatePassword(changeableMemberData.getPassword()))
            throw new ExceptionHandler(ErrorStatus.MALFORMED_MEMBER_PASSWORD);
    }

}
