package com.brian.stylish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private String id;
    private String name;
    private Integer price;
    private ColorDTO color;
    private String size;
    private Integer qty;
}
