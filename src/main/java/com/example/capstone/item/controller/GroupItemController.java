package com.example.capstone.item.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.item.dto.GroupItemResponseDTO;
import com.example.capstone.item.dto.ItemResponseDTO;
import com.example.capstone.item.service.GroupPurchaseItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groupItem")
public class GroupItemController {

    private final GroupPurchaseItemService groupPurchaseItemService;

    @GetMapping
    public ApiResponse<GroupItemResponseDTO.GroupItemList> getGroupItemList(@Valid @Min(1) @RequestParam(name = "page") Integer page,
                                                                            @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(groupPurchaseItemService.getGroupItemList(page - 1, size));
    }

    @GetMapping("/{groupItemId}")
    public ApiResponse<GroupItemResponseDTO.GroupItemWithSellerAndRemains> searchGroupItemWithSellerAndRemains(@Valid @PathVariable Long groupItemId) {

        return ApiResponse.onSuccess(groupPurchaseItemService.getDetailOfGroupItem(groupItemId));

    }

}
