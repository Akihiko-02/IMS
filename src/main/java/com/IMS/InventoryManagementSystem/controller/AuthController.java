package com.IMS.InventoryManagementSystem.controller;


import com.IMS.InventoryManagementSystem.dtos.LoginRequest;
import com.IMS.InventoryManagementSystem.dtos.RegisterRequest;
import com.IMS.InventoryManagementSystem.dtos.Response;
import com.IMS.InventoryManagementSystem.services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return  ResponseEntity.ok(userServices.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return  ResponseEntity.ok(userServices.loginUser(loginRequest));
    }
}
