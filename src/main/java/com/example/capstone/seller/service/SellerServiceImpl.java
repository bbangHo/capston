package com.example.capstone.seller.service;

import com.example.capstone.seller.Seller;
import com.example.capstone.seller.dto.TempSellerResponseDTO;
import com.example.capstone.seller.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.capstone.seller.converter.SellerConverter.toSellerResponseDTOList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;

    @Override
    public TempSellerResponseDTO.SellerList searchSubscribedSeller(Long memberId, Integer page, Integer size){

        log.info("sellerService searchSubscribedSeller start.............. ");

        Page<Seller> sellers = sellerRepository.findSubscribedSeller(memberId, PageRequest.of(page, size, Sort.by("createdAt").ascending()));

        return toSellerResponseDTOList(sellers);

    }


}
