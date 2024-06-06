package com.example.capstone.member.service;

import com.example.capstone.member.dto.MemberRequestDTO;
import com.example.capstone.member.dto.MemberResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    MemberResponseDTO.MemberState changeMemberRole(Long memberId, MemberRequestDTO.ToSeller request,
                                                   MultipartFile multipartFile);

    MemberResponseDTO.DupCheckField checkField(MemberRequestDTO.DupCheckField dupCheckFields);

    void deleteTempMember(Long id);

    MemberResponseDTO.SignUpMember signUp (MemberRequestDTO.SignUpMember signUpMember);

    MemberRequestDTO.ChangeableMemberData changeMemberData(Long id, MemberRequestDTO.ChangeableMemberData changeableMemberData);

    MemberRequestDTO.ChangeableMemberData getMemberData(Long id);

    MemberRequestDTO.ChangeableMemberData passwordCheck(Long id, String password);

}
