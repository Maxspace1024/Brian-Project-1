package com.brian.stylish.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignCreateDTO {
    @Min(value = 1)
    private Integer productId;
    @NotNull
    private MultipartFile picture;
    @NotNull
    private String story;
}
