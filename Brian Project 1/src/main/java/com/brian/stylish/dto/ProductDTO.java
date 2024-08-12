package com.brian.stylish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Integer id;
    private String category;
    private String title;
    private String description;
    private Integer price;
    private String texture;
    private String wash;
    private String place;
    private String note;
    private String story;
    private List<ColorDTO> colors;
    private List<String> sizes;
    private List<VariantDTO> variants;
    @JsonProperty("main_image")
    private String mainImage;
    private List<String> images;
}
