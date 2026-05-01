package com.ver.ssms.dto.auth;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    @NotEmpty(message = "Username field can't be empty")
    private String username;

    @NotEmpty(message = "Password field can't be empty")
    private String password;
}
