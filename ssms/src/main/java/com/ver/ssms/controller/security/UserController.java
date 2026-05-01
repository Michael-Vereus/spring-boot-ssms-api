package com.ver.ssms.controller.security;

import com.ver.ssms.dto.BodyResponse;
import com.ver.ssms.dto.auth.LoginUser;
import com.ver.ssms.dto.auth.RegisterUser;
import com.ver.ssms.model.UserEntity;
import com.ver.ssms.service.UserService;
import com.ver.ssms.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ssms/api/auth")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<BodyResponse<?>> registerUser(@Valid @RequestBody RegisterUser registerUser){
        return ResponseEntity.ok(BodyResponse.success(Map.of("user", userService.register(UserEntity.signUp(registerUser)))));
    }

    @PostMapping("/login")
    public ResponseEntity<BodyResponse<?>> loginUser(@Valid @RequestBody LoginUser loginUser){
        String token = authService.login(loginUser.getUsername(), loginUser.getPassword());

        return ResponseEntity.ok(BodyResponse.success(Map.of("token", token)));
    }
}
