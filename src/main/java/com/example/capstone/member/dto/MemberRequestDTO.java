package com.example.capstone.member.dto;

import lombok.*;

public class MemberRequestDTO {

    /**
     * checkType은 loginId와 nickName 중 중복 체크할 항목을 나타냅니다.
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DupCheckField {

        private Long id;

        private String type;

        private String loginId;

        private String nickName;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpMember{

        private Long id;

        private String loginId;

        private String password;

        private String name;

        private String nickName;

        private String phone;

        private String address;

        private String details;
    }

}
