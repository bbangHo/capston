package com.example.capstone.member.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.member.dto.AlarmResponseDTO;
import com.example.capstone.member.service.AlarmService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ApiResponse<AlarmResponseDTO.AlarmList> getAlarmList(@RequestParam(name = "type") Integer type,
                                                                @RequestParam(name = "memberId",required = false) Long memberId,
                                                                @Min(1) @RequestParam(name = "page") Integer page,
                                                                @Positive @RequestParam(name = "size") Integer size) {

        if (type == 0)
            return ApiResponse.onSuccess(alarmService.getAlarmList(memberId, page - 1, size));
        else
            return null;
    }

    @GetMapping("/{memberId}")
    public ApiResponse<Integer> getAllNotConfirmedAlarmNum(@PathVariable Long memberId) {

        return ApiResponse.onSuccess(alarmService.countAllNotConfirmedAlarm(memberId));
    }
}
