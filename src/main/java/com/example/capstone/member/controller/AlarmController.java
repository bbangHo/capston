package com.example.capstone.member.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.member.dto.AlarmResponseDTO;
import com.example.capstone.member.service.AlarmService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import com.example.capstone.util.AuthenticatedMemberUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/alarms")
    public ApiResponse<AlarmResponseDTO.AlarmList> getAlarmList(@AuthenticationPrincipal MemberSecurityDTO member,
                                                                @Min(1) @RequestParam(name = "page") Integer page,
                                                                @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(alarmService.getAlarmList(member.getId(), page - 1, size));

    }

    @GetMapping("/alarm")
    public ApiResponse<Integer> getAllNotConfirmedAlarmNum(@AuthenticationPrincipal MemberSecurityDTO member) {

        return ApiResponse.onSuccess(alarmService.countAllNotConfirmedAlarm(member.getId()));
    }

}
