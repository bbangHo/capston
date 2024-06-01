package com.example.capstone.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class UtilList {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjectList<T> {
        private Integer listSize;
        private Integer page;
        private Long totalElement;
        private Boolean isFirst;
        private Boolean isLast;
        private List<T> List;
    }
}
