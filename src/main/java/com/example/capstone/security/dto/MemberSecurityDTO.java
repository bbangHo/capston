package com.example.capstone.security.dto;

import com.example.capstone.member.common.MemberType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String loginId;
    private String passwd;
    private Long memberId;

    public MemberSecurityDTO(String loginId, String passwd, Collection<GrantedAuthority> authorities, Long memberId) {
        super(loginId,passwd,authorities);
        this.loginId = loginId;
        this.passwd = passwd;
        this.memberId = memberId;
    }



}
