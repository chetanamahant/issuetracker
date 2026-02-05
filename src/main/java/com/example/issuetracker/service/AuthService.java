package com.example.issuetracker.service;

import com.example.issuetracker.dto.request.LoginRequest;
import com.example.issuetracker.dto.request.RegisterRequest;
import com.example.issuetracker.dto.response.AuthResponse;
import com.example.issuetracker.dto.response.LoginResponse;
import com.example.issuetracker.entity.User;

import java.util.List;

public interface AuthService {
    User register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, RegisterRequest request);
    void deleteUser(Long id);

}
