package com.brian.stylish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantCreateDTO {
    private String color_name;
    private String color_code;
    private String size;
    private Integer stock;
}