package com.example.capstone.member.service;

import com.example.capstone.member.dto.MemberRequestDTO;
import com.example.capstone.member.dto.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO.MemberState changeMemberRole(Long memberId);

    MemberResponseDTO.DupCheckField checkField(MemberRequestDTO.DupCheckField dupCheckFields);

    void deleteTempMemberWithLoginId(String loginId);

    void deleteTempMemberWithNickName(String nickName);

    MemberResponseDTO.SignUpMember signUp (MemberRequestDTO.SignUpMember signUpMember);

    void changeNickName(String nickName);

    }
