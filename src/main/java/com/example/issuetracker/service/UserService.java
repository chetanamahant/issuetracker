package com.example.issuetracker.service;

import com.example.issuetracker.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);
}
