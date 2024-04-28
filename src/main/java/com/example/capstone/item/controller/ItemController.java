package com.example.capstone.item.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ApiResponse<List<ItemResponseDTO.Item>> getItemList(@Valid @Positive @RequestParam(name = "category-id") Long categoryId,
                                                               @Min(1) @RequestParam(name = "page") Integer page,
                                                               @Positive @RequestParam(name = "size") Integer size) {
        return ApiResponse.onSuccess(itemService.getItemList(categoryId, page, size));
    }
}
