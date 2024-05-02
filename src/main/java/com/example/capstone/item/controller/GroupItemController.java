package com.example.capstone.item.controller;

import com.example.capstone.apiPayload.ApiResponse;
import com.example.capstone.item.dto.GroupItemResponseDTO;
import com.example.capstone.item.service.GroupPurchaseItemService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groupItem")
public class GroupItemController {

    private final GroupPurchaseItemService groupPurchaseItemService;

    @GetMapping
    public ApiResponse<GroupItemResponseDTO.GroupItemList> getGroupItemList(@Min(1) @RequestParam(name = "page") Integer page,
                                                                            @Positive @RequestParam(name = "size") Integer size) {

        return ApiResponse.onSuccess(groupPurchaseItemService.getGroupItemList(page - 1, size));
    }
}
