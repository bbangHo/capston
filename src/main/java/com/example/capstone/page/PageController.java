package com.example.capstone.page;

import com.example.capstone.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/")
    public ApiResponse<PageResponseDTO.Main> getMainPage() {
        return ApiResponse.onSuccess(pageService.getMainPage(1L));     // TODO: 로그인 구현되어야함
    }
}
