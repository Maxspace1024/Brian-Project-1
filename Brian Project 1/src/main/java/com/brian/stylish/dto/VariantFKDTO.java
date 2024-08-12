package com.brian.stylish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantFKDTO {
    private Integer id;
    private Integer colorsId;
    private Integer sizesId;
    private Integer stock;
    private Integer productId;
}
