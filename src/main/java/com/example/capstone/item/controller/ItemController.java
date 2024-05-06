package com.example.capstone.item.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.service.ItemQueryService;
import com.example.capstone.item.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemQueryService itemQueryService;
    private final ItemService itemService;

    @GetMapping
    public ApiResponse<ItemResponseDTO.ItemList> getItemList(@Valid @Positive @RequestParam(name = "category-id") Long categoryId,
                                                             @Min(1) @RequestParam(name = "page") Integer page,
                                                             @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(itemService.getItemList(categoryId, page - 1, size));
    }

    @GetMapping("/deadline")
    public ApiResponse<ItemResponseDTO.ItemList> getImminentItemList(@Min(1) @RequestParam(name = "page") Integer page,
                                                             @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(itemService.getImminentItemList(page - 1, size));
    }
    @GetMapping("/ranking")
    public ApiResponse<ItemResponseDTO.ItemList> getPopularItemList(@Min(1) @RequestParam(name = "page") Integer page,
                                                                     @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(itemService.getPopularItemList(page - 1, size));
    }

    @GetMapping("/subscription")
    public ApiResponse<ItemResponseDTO.ItemList> getSubscribedItemList(@RequestParam(name = "type") Integer type,
                                                                       @RequestParam(name = "fromMember",required = false) Long fromMember,
                                                                       @Min(1) @RequestParam(name = "page") Integer page,
                                                                       @Positive @RequestParam(name = "size") Integer size) {
        if (type == 0)
            return ApiResponse.onSuccess(itemService.getSubscribedItemList(fromMember, page - 1, size));
        else
            return ApiResponse.onSuccess(itemService.getAllItemList(page - 1, size));
    }

    @GetMapping("/search")
    public ApiResponse<ItemResponseDTO.ItemList> searchItemList(@Valid @RequestParam(name = "keyword") String keyword,
                                                                @Min(1) @RequestParam(name = "page") Integer page,
                                                                @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(itemQueryService.searchItemList(keyword, page - 1, size));

    }

    @GetMapping("/{itemId}")
    public ApiResponse<ItemResponseDTO.DetailsOfItem> searchDetailOfItem(@PathVariable Long itemId) {

        return ApiResponse.onSuccess(itemService.getDetailOfItem(itemId));

    }

}
