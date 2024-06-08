package com.example.capstone.item.service;

import com.example.capstone.common.OrderedMultipartFileDTO;
import com.example.capstone.item.dto.ItemRequestDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemResponseDTO.ItemList getItemList(Long categoryId, Integer page, Integer size);

    ItemResponseDTO.ItemList getImminentItemList(Integer page, Integer size);


    ItemResponseDTO.ItemList getPopularItemList(Integer page, Integer size);

    ItemResponseDTO.ItemList getSubscribedItemList(Long fromMember,Integer page, Integer size);


    ItemResponseDTO.ItemList getAllItemList(Integer page, Integer size);

    ItemResponseDTO.DetailsOfItem getDetailOfItem(Long ItemId);
    ItemResponseDTO.ItemUpload uploadItem(Long memberId, ItemRequestDTO.ItemUpload request, List<MultipartFile> itemImages, MultipartFile itemDetailsImage);
    ItemResponseDTO.ItemUpload uploadItemV1(Long memberId, ItemRequestDTO.ItemUpload request);
}
