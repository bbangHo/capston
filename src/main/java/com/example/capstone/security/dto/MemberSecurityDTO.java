package com.example.capstone.security.dto;

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

    public MemberSecurityDTO( String loginId, String passwd, Collection<GrantedAuthority> authorities) {
        super(loginId,passwd,authorities);
        this.loginId = loginId;
        this.passwd = passwd;
    }



}
