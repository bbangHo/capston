package com.example.capstone.seller.converter;


import com.example.capstone.seller.Seller;
import com.example.capstone.seller.SellerResponseDTO;


public class SellerConverter {

    public static SellerResponseDTO.Seller toSellerResponseDTO(Seller seller) {

        if (seller == null) {
            return null;
        }

        return SellerResponseDTO.Seller.builder()
                .id(seller.getId())
                .details(seller.getDetails())
                .imageUrl(seller.getImageUrl())
                .introduction(seller.getIntroduction())
                .build();
    }



}
