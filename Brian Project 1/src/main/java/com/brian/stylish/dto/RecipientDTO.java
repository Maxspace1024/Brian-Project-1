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
public class RecipientDTO {
    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private String address;
    @NotNull
    private String time;
}
