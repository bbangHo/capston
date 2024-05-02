package com.example.capstone.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AlarmResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Alarm {

        private Long id;

        private Long memberId;

        private Boolean isConfirmed;

        private String title;

        private String content;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmList {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<Alarm> alarmList;
    }
}
