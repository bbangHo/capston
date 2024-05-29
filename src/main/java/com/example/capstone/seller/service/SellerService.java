package com.example.capstone.seller.service;

import com.example.capstone.seller.dto.TempSellerResponseDTO;

public interface SellerService {

    TempSellerResponseDTO.SellerList searchSubscribedSeller(Long memberId, Integer page, Integer size);
}
