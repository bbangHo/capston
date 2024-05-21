package com.example.capstone.member.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.member.dto.AlarmResponseDTO;
import com.example.capstone.member.service.AlarmService;
import com.example.capstone.security.dto.MemberSecurityDTO;
import com.example.capstone.util.AuthenticatedMemberId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ApiResponse<AlarmResponseDTO.AlarmList> getAlarmList(@RequestParam(name = "type") Integer type,
                                                                @Min(1) @RequestParam(name = "page") Integer page,
                                                                @Positive @RequestParam(name = "size") Integer size) {

        Long memberId = AuthenticatedMemberId.getMemberId();
        if (type == 0)
            return ApiResponse.onSuccess(alarmService.getAlarmList(memberId, page - 1, size));
        else
            return null;
    }

    @GetMapping("/{memberId}")
    public ApiResponse<Integer> getAllNotConfirmedAlarmNum(@PathVariable Long memberId) {

        return ApiResponse.onSuccess(alarmService.countAllNotConfirmedAlarm(memberId));
    }

    public Long getMemberId (){
        return ((MemberSecurityDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
