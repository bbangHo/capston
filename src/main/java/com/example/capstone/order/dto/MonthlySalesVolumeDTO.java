package com.example.capstone.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySalesVolumeDTO {
    private String month;
    private Integer salesVolume;
}
