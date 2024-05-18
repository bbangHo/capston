package com.example.capstone.seller.dto;

import com.example.capstone.item.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostUpload {
        private String content;

        @Size(max = 10, message = "최대 10개의 사진만 업로드할 수 있습니다.")
        private List<MultipartFile> multipartFileList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemUpload {
        @NotNull(message = "상품 이름은 필수입니다.")
        private String itemName;
        private String simpleExplanation;

        @Positive
        private Long categoryId;

        @Positive
        private Integer price;

        @NotNull(message = "공동 구매 여부는 필수입니다.")
        private Boolean isGroupPurchase;
        private String detailExplanation;

        @Size(max = 10, message = "최대 10개의 사진만 업로드할 수 있습니다.")
        private List<MultipartFile> multipartFileList;
    }
}
