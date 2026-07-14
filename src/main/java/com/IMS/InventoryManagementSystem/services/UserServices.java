package com.IMS.InventoryManagementSystem.services;

import com.IMS.InventoryManagementSystem.dtos.LoginRequest;
import com.IMS.InventoryManagementSystem.dtos.RegisterRequest;
import com.IMS.InventoryManagementSystem.dtos.Response;
import com.IMS.InventoryManagementSystem.dtos.UserDTO;
import com.IMS.InventoryManagementSystem.models.User;

public interface UserServices {

    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    User getCurrentLogginUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransactions(Long id);
    Response getAllUsers();
}
