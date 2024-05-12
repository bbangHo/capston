package com.example.capstone.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SellerResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Seller {

        private Long id;

        private String introduction;

        private String details;

        private String imageUrl;

    }


}
