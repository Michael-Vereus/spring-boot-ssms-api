package com.ver.ssms.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
public class RegisterUser {

    @Null(message = "User Id MUST BE EMPTY !")
    private String userId;
    @NotEmpty(message = "Username CAN'T BE EMPTY !")
    private String username;
    @NotEmpty(message = "Password CAN'T BE EMPTY !")
    private String password;
}
