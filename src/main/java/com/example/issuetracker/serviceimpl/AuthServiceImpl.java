package com.example.issuetracker.serviceimpl;

import com.example.issuetracker.dto.request.LoginRequest;
import com.example.issuetracker.dto.request.RegisterRequest;

import com.example.issuetracker.dto.response.LoginResponse;
import com.example.issuetracker.entity.User;
import com.example.issuetracker.enums.Role;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.UserRepository;
import com.example.issuetracker.service.AuthService;
import com.example.issuetracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.issuetracker.exception.AdminAlreadyExistsException;
import com.example.issuetracker.exception.DuplicateUserException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwt;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(u -> {
                    throw new RuntimeException("Username already exists");
                });

        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists");
                });

        String roleValue = request.getRole() == null
                ? "DEVELOPER"
                : request.getRole().toUpperCase();

        Role role;
        try {
            role = Role.valueOf(roleValue);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + roleValue);
        }

        if (role == Role.ADMIN && userRepository.existsByRole(Role.ADMIN)) {
            throw new RuntimeException("Only one admin allowed");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setEnabled(true);

        return userRepository.save(user);
    }



    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        String role = String.valueOf(user.getRole());

        String token = jwt.generateToken(user.getUsername() , role);

        return LoginResponse.builder()
                .statusCode(200)
                .token(token)   // JWT will be added later
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User updateUser(Long id, RegisterRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        // 🔴 Username duplicate check
        userRepository.findByUsername(request.getUsername())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(u -> {
                    throw new DuplicateUserException("Username already exists");
                });

        // 🔴 Email duplicate check
        userRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(u -> {
                    throw new DuplicateUserException("Email already exists");
                });

        // ✅ Role normalize
        String roleValue = request.getRole() == null
                ? user.getRole().name()
                : request.getRole().replace("ROLE_", "").toUpperCase();

        Role role;
        try {
            role = Role.valueOf(roleValue);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid role: " + request.getRole());
        }

        // 🔴 Only one ADMIN allowed
        if (role == Role.ADMIN
                && user.getRole() != Role.ADMIN
                && userRepository.existsByRole(Role.ADMIN)) {

            throw new AdminAlreadyExistsException("Only one ADMIN is allowed");
        }

        // ✅ Update fields
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(role);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }
}