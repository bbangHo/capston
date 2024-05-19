package com.example.capstone.security.service;

import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.member.Member;
import com.example.capstone.member.repository.MemberRepository;
import com.example.capstone.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.capstone.apiPayload.code.status.ErrorStatus.MEMBER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findMemberByLoginId(username);

        Member member = result.orElseThrow(() -> new UsernameNotFoundException("MEMBER_NOT_FOUND"));
        log.info("MemberDetailsService Member-------------------");

        return new MemberSecurityDTO(
                member.getLoginId(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority(member.getType().toString())));
    }
}
