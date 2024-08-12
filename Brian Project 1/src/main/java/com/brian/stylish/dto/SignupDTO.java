package com.brian.stylish.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDTO {
    @NotBlank(message = "name is blank")
    private String name;
    @Email(message = "invalid email")
    private String email;
    @Size(min = 8, message = "password length should equal or more than 8")
    private String password;
}
