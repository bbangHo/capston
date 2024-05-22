package com.example.capstone.util;

import com.example.capstone.security.dto.MemberSecurityDTO;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedMemberUtil {

    public static Long getMemberId (){
        return ((MemberSecurityDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
