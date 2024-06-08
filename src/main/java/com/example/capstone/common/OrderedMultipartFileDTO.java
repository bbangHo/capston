package com.example.capstone.common;

import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedMultipartFileDTO {
    @Min(1)
    private Integer sequence;
    private MultipartFile multipartFile;
}
