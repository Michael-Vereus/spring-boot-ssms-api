package com.ver.ssms.controller.security;

import com.ver.ssms.dto.BodyResponse;
import com.ver.ssms.dto.auth.RegisterUser;
import com.ver.ssms.model.UserEntity;
import com.ver.ssms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ssms/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<BodyResponse<?>> registerUser(@Valid @RequestBody RegisterUser registerUser){
        return ResponseEntity.ok(BodyResponse.success(Map.of("String", userService.register(UserEntity.signUp(registerUser)))));
    }
}
