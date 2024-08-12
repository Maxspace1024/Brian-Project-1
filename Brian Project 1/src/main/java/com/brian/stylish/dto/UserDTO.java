package com.brian.stylish.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserDTO {
    private Integer id;
    private String provider;
    private String name;
    private String email;
    private String password;
    private String picture;
}
