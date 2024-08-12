package com.brian.stylish.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutDTO {
    @NotNull
    private String prime;
    @NotNull
    private OrderDTO order;
}
