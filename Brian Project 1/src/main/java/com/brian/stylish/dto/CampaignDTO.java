package com.brian.stylish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignDTO {
    @JsonProperty("product_id")
    private Integer productId;
    private String picture;
    private String story;
}
