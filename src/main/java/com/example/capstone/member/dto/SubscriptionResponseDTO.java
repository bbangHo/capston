package com.example.capstone.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SubscriptionResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Subscription {
        private Boolean isSubscribe;
        private LocalDateTime performedAt ;
        private Long fromMemberId;
        private Long toMemberId;
    }
}
