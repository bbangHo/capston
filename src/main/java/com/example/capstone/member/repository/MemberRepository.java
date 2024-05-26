package com.example.capstone.member.repository;

import com.example.capstone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByLoginId(String username);

    Optional<Member> findMemberByNickName(String nickName);

    void deleteMemberByLoginId(String loginId);

    void deleteMemberByNickName(String nickName);

}
