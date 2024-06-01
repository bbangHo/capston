package com.example.capstone.member.service;

import com.example.capstone.member.Alarm;
import com.example.capstone.member.converter.AlarmConverter;
import com.example.capstone.member.dto.AlarmResponseDTO;
import com.example.capstone.member.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public AlarmResponseDTO.AlarmList getAlarmList(Long memberId, Integer page, Integer size) {

        Page<Alarm> alarmPage = alarmRepository.searchNotConfirmedAlarm(memberId, PageRequest.of(page, size));

        for(Alarm alarm: alarmPage.getContent()){

            alarm.changeConfirmation();
            alarmRepository.save(alarm);

        }

        return AlarmConverter.toAlarmList(alarmPage);
    }

    public Integer countAllNotConfirmedAlarm(Long memberId){

        return alarmRepository.countAllNotConfirmedAlarm(memberId);
    }

}
