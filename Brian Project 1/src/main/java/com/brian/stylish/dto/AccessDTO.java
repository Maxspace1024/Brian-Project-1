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
public class AccessDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_expired")
    private Integer accessExpired;
    private UserDTO user;
}
