package com.example.capstone.member.converter;


import com.example.capstone.member.Alarm;
import com.example.capstone.member.dto.AlarmResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public class AlarmConverter {

    public static AlarmResponseDTO.Alarm toAlarmResponseDTO(Alarm alarm) {
        return AlarmResponseDTO.Alarm.builder()
                .id(alarm.getId())
                .content(alarm.getContent())
                .title(alarm.getTitle())
                .isConfirmed(alarm.getIsConfirmed())
                .memberId(alarm.getMember().getId())
                .build();
    }

    public static AlarmResponseDTO.AlarmList toAlarmList (Page<Alarm> alarmPage) {
        List< AlarmResponseDTO.Alarm> alarmList = alarmPage.stream()
                .map(AlarmConverter::toAlarmResponseDTO)
                .toList();


        return AlarmResponseDTO.AlarmList.builder()
                .listSize(alarmPage.getSize())
                .page(alarmPage.getTotalPages())
                .totalElement(alarmPage.getTotalElements())
                .isFirst(alarmPage.isFirst())
                .isLast(alarmPage.isLast())
                .alarmList(alarmList)
                .build();
    }
}
