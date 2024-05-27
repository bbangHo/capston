package com.example.capstone.member.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.common.QueryService;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.member.Address;
import com.example.capstone.member.Member;
import com.example.capstone.member.common.MemberType;
import com.example.capstone.member.converter.MemberConverter;
import com.example.capstone.member.dto.AddressDTO;
import com.example.capstone.member.dto.MemberRequestDTO;
import com.example.capstone.member.dto.MemberResponseDTO;
import com.example.capstone.member.repository.AddressRepository;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.seller.Seller;
import com.example.capstone.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.capstone.member.converter.MemberConverter.toAddress;
import static com.example.capstone.member.converter.MemberConverter.toSignUpMember;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final QueryService queryService;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public MemberResponseDTO.MemberState changeMemberRole(Long memberId) {
        Member member = queryService.findMember(memberId);

        if (member.getType() == MemberType.ROLE_SELLER) {
            throw new GeneralException(ErrorStatus.MEMBER_ALREADY_SELLER);
        }

        member.changeRole();
        memberRepository.save(member);

        Seller seller = Seller.builder()
                .member(member)
                .build();

        sellerRepository.save(seller);

        return MemberConverter.toMemberState(member);
    }

    @Override
    public MemberResponseDTO.DupCheckField checkField (MemberRequestDTO.DupCheckField dupCheckFields) {

        log.info("DupCheck service start............");
        Member member=null;

        if (dupCheckFields.getType().equals("loginId")){

            member = checkLoginId(dupCheckFields);

        } else if(dupCheckFields.getType().equals("nickName")) {

            member = checkNickName(dupCheckFields);

        } else
            throw new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND);

        Member savedMember = memberRepository.save(member);
        log.info("DupCheck service end............");

        return MemberResponseDTO.DupCheckField.builder()
                .id(savedMember.getId())
                .type(dupCheckFields.getType())
                .loginId(savedMember.getLoginId())
                .nickName(savedMember.getNickName())
                .build();
    }

    @Override
    public void deleteTempMemberWithLoginId(String loginId) {

        log.info("deleteTempMemberWithLoginId service start............");

        memberRepository.deleteMemberByLoginId(loginId);

    }

    @Override
    public void deleteTempMemberWithNickName(String nickName) {
        log.info("deleteTempMemberWithNickName service start............");

        memberRepository.deleteMemberByLoginId(nickName);
    }

    @Override
    public MemberResponseDTO.SignUpMember signUp (MemberRequestDTO.SignUpMember signUpMember) {

        log.info("signUp service start............");

        Member tempMember = memberRepository.findById(signUpMember.getId())
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (!tempMember.getLoginId().equals(signUpMember.getLoginId())){

            throw new ExceptionHandler(ErrorStatus.NOT_CHECKED_LOGINID);

        } else if(!tempMember.getNickName().equals(signUpMember.getNickName())){

            throw new ExceptionHandler(ErrorStatus.NOT_CHECKED_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(signUpMember.getPassword());

        Member savedMember = memberRepository.save(toSignUpMember(signUpMember,encodedPassword));

        AddressDTO.Address address = AddressDTO.Address.builder()
                .member(savedMember)
                .address(signUpMember.getAddress())
                .details(signUpMember.getDetails())
                .build();

        Address savedAddress = addressRepository.save(toAddress(address));

        log.info("signUp service end............");

        return MemberResponseDTO.SignUpMember.builder()
                .id(savedMember.getId())
                .loginId(savedMember.getLoginId())
                .password(savedMember.getPassword())
                .name(savedMember.getName())
                .nickName(savedMember.getNickName())
                .phone(savedMember.getPhone())
                .address(savedAddress.getAddress())
                .details(savedAddress.getDetails())
                .build();

    }

    @Override
    public void changeNickName(String nickName) {

    }


    private Member checkLoginId (MemberRequestDTO.DupCheckField dupCheckField) {

        Optional<Member> memberByLoginId = memberRepository.findMemberByLoginId(dupCheckField.getLoginId());

        //중복된 로그인 ID가 존재하지 않을 때
        if(memberByLoginId.isEmpty()){
            return getMemberByNickName(dupCheckField);

        //중복된 로그인 ID가 존재할 때
        } else
            throw new ExceptionHandler(ErrorStatus.DUPLICATED_LOGINID);
    }

    private Member checkNickName (MemberRequestDTO.DupCheckField dupCheckField){

        Optional<Member> memberByNickName = memberRepository.findMemberByNickName(dupCheckField.getNickName());

        //중복된 닉네임이 존재하지 않을 때
        if(memberByNickName.isEmpty()){
            return getMemberByLoginId(dupCheckField);

            //중복된 닉네임이 존재할 때
        } else
            throw new ExceptionHandler(ErrorStatus.DUPLICATED_NICKNAME);

    }

    private Member getMemberByNickName(MemberRequestDTO.DupCheckField dupCheckField){
        if(dupCheckField.getNickName().isEmpty())
            return Member.builder()
                    .loginId(dupCheckField.getLoginId())
                    .build();

        Member searchedMember = memberRepository.findMemberByNickName(dupCheckField.getNickName())
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return Member.builder()
                .id(searchedMember.getId())
                .loginId(dupCheckField.getLoginId())
                .nickName(searchedMember.getNickName())
                .build();
    }

    private Member getMemberByLoginId(MemberRequestDTO.DupCheckField dupCheckField){

        if(dupCheckField.getLoginId().isEmpty())
            return Member.builder()
                    .nickName(dupCheckField.getNickName())
                    .build();

        Member searchedMember = memberRepository.findMemberByLoginId(dupCheckField.getLoginId())
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return Member.builder()
                .id(searchedMember.getId())
                .loginId(searchedMember.getLoginId())
                .nickName(dupCheckField.getNickName())
                .build();

    }


}
