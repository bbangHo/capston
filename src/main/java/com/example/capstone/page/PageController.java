package com.example.capstone.page;

import com.example.capstone.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/main")
    public ApiResponse<PageResponseDTO.Main> getMainPage() {
        return ApiResponse.onSuccess(pageService.getMainPage(1L));     // TODO: 로그인 구현되어야함
    }

    @GetMapping("/intro/{member-id}")
    public ApiResponse<PageResponseDTO.PostPreviews> getIntroPage(@PathVariable("member-id") Long memberId) {
        PageResponseDTO.PostPreviews response = pageService.getIntroPage(memberId);
        return ApiResponse.onSuccess(response);
    }

}
