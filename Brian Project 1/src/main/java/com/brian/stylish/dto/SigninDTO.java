package com.brian.stylish.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SigninDTO {
    @NotBlank(message = "provider is blank")
    private String provider;
    private String email;
    private String password;
    @JsonProperty("access_token")
    private String accessToken;
}
