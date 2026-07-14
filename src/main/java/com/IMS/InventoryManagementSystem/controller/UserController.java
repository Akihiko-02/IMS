package com.IMS.InventoryManagementSystem.controller;


import com.IMS.InventoryManagementSystem.dtos.LoginRequest;
import com.IMS.InventoryManagementSystem.dtos.RegisterRequest;
import com.IMS.InventoryManagementSystem.dtos.Response;
import com.IMS.InventoryManagementSystem.dtos.UserDTO;
import com.IMS.InventoryManagementSystem.models.User;
import com.IMS.InventoryManagementSystem.services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        return  ResponseEntity.ok(userServices.getAllUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return  ResponseEntity.ok(userServices.updateUser(id, userDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        return  ResponseEntity.ok(userServices.deleteUser(id));
    }

    @GetMapping("/transactions/{userID}")
    public ResponseEntity<Response> getUserAndTransactions(@PathVariable Long userID) {
        return  ResponseEntity.ok(userServices.getUserTransactions(userID));
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        return  ResponseEntity.ok(userServices.getCurrentLogginUser());
    }
}
