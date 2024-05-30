package com.example.capstone.seller.converter;


import com.example.capstone.item.Item;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.seller.Seller;
import com.example.capstone.seller.dto.TempSellerResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public class SellerConverter {

    public static TempSellerResponseDTO.Seller toSellerResponseDTO(Seller seller) {

        if (seller == null) {
            return null;
        }

        return TempSellerResponseDTO.Seller.builder()
                .id(seller.getId())
                .details(seller.getDetails())
                .imageUrl(seller.getImageUrl())
                .introduction(seller.getIntroduction())
                .build();
    }

    public static Seller toSeller(TempSellerResponseDTO.Seller seller) {

        if (seller == null) {
            return null;
        }

        return Seller.builder()
                .id(seller.getId())
                .details(seller.getDetails())
                .imageUrl(seller.getImageUrl())
                .introduction(seller.getIntroduction())
                .build();
    }

    public static TempSellerResponseDTO.SellerList toSellerResponseDTOList(Page<Seller> sellerPage) {
        List<TempSellerResponseDTO.Seller> sellerList = sellerPage.stream()
                .map(SellerConverter::toSellerResponseDTO)
                .toList();

        return TempSellerResponseDTO.SellerList.builder()
                .listSize(sellerPage.getSize())
                .page(sellerPage.getTotalPages())
                .totalElement(sellerPage.getTotalElements())
                .isFirst(sellerPage.isFirst())
                .isLast(sellerPage.isLast())
                .sellerList(sellerList)
                .build();
    }
}






