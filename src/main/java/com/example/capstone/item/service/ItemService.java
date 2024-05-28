package com.example.capstone.item.service;

import com.example.capstone.apiPayload.code.status.ErrorStatus;
import com.example.capstone.exception.GeneralException;
import com.example.capstone.exception.handler.ExceptionHandler;
import com.example.capstone.item.Category;
import com.example.capstone.item.Item;
import com.example.capstone.item.converter.ItemConverter;
import com.example.capstone.item.dto.ItemRequestDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.capstone.apiPayload.code.status.ErrorStatus.MEMBER_NOT_FOUND;

public interface ItemService {

    ItemResponseDTO.ItemList getItemList(Long categoryId, Integer page, Integer size);

    ItemResponseDTO.ItemList getImminentItemList(Integer page, Integer size);


    ItemResponseDTO.ItemList getPopularItemList(Integer page, Integer size);

    ItemResponseDTO.ItemList getSubscribedItemList(Long fromMember,Integer page, Integer size);


    ItemResponseDTO.ItemList getAllItemList(Integer page, Integer size);

    ItemResponseDTO.DetailsOfItem getDetailOfItem(Long ItemId);
    ItemResponseDTO.ItemUpload uploadItem(Long memberId, ItemRequestDTO.ItemUpload request, List<MultipartFile> multipartFiles);
}
