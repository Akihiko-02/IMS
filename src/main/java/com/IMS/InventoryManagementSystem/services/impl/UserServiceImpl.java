package com.IMS.InventoryManagementSystem.services.impl;

import com.IMS.InventoryManagementSystem.dtos.LoginRequest;
import com.IMS.InventoryManagementSystem.dtos.RegisterRequest;
import com.IMS.InventoryManagementSystem.dtos.Response;
import com.IMS.InventoryManagementSystem.dtos.UserDTO;
import com.IMS.InventoryManagementSystem.enums.UserRole;
import com.IMS.InventoryManagementSystem.exceptions.InvalidCredentialsException;
import com.IMS.InventoryManagementSystem.exceptions.NotFoundException;
import com.IMS.InventoryManagementSystem.models.User;
import com.IMS.InventoryManagementSystem.repositories.UserRepository;
import com.IMS.InventoryManagementSystem.security.JwtUtils;
import com.IMS.InventoryManagementSystem.services.UserServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role = UserRole.MANAGER;
        if(registerRequest.getRole() != null){
            role = registerRequest.getRole();
        }
        User userToSave = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();
        userRepository.save(userToSave);
        return Response.builder()
                .status(200)
                .message("user created successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new NotFoundException("Email not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw  new InvalidCredentialsException("Password does not match");
        }
        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("user logged in successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 month")
                .build();
    }

    @Override
    public User getCurrentLogginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("Email not found"));
        user.setTransactions(null);
        return user;
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        if(userDTO.getName() != null){
            existingUser.setEmail(userDTO.getEmail());
        }
        if(userDTO.getName() != null){
            existingUser.setName(userDTO.getName());
        }
        if(userDTO.getPhoneNumber() != null){
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        }
        if(userDTO.getRole() != null){
            existingUser.setRole(userDTO.getRole());
        }

        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("User successfully updated")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));

        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("User successfully deleted")
                .build();
    }

    @Override
    public Response getUserTransactions(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.getTransactions().forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setSuppliers(null);
        });
        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<UserDTO> userDTOS = modelMapper.map(
                users,
                new TypeToken<List<UserDTO>>() {}.getType()
        );
        userDTOS.forEach(userDTO -> userDTO.setTransactions(null));
        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOS)
                .build();
    }
}
