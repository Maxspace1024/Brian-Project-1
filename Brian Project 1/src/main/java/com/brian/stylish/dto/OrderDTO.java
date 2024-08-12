package com.brian.stylish.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    @NotNull
    private String shipping;
    @NotNull
    private String payment;
    @NotNull
    private Integer subtotal;
    @NotNull
    private Integer freight;
    @NotNull
    private Integer total;
    @NotNull
    private RecipientDTO recipient;
    @NotNull
    private List<OrderDetailDTO> list;
}
