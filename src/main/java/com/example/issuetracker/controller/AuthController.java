package com.example.issuetracker.controller;


import com.example.issuetracker.dto.request.LoginRequest;
import com.example.issuetracker.dto.request.RegisterRequest;
import com.example.issuetracker.dto.response.APIResponse;
import com.example.issuetracker.dto.response.LoginResponse;
import com.example.issuetracker.entity.User;
import com.example.issuetracker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication APIs",
        description = "User registration and login APIs"
)
public class AuthController {

    private final AuthService authService;

    // ✅ REGISTER USER
    @Operation(
            summary = "Register new user",
            description = "Creates a new user account using email and password"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<APIResponse<User>> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration request",
                    required = true
            )
            @Valid @RequestBody RegisterRequest request
    ) {
        User data = authService.register(request);

        return ResponseEntity.ok(
                new APIResponse<>(
                        true,
                        "Register successfully",
                        data,
                        LocalDateTime.now()
                )
        );
    }

    // ✅ LOGIN USER
    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns login response (JWT / user info)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginResponse>> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User login request",
                    required = true
            )
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                new APIResponse<>(
                        true,
                        "Login successful",
                        response,
                        LocalDateTime.now()
                )
        );
    }



    // ===================== GET ALL USERS =====================

    @Operation(
            summary = "Get all users",
            description = "Fetches list of all users"
    )
    @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<APIResponse<List<User>>> getAllUsers() {

        return ResponseEntity.ok(
                new APIResponse<>(
                        true,
                        "Users fetched",
                        authService.getAllUsers(),
                        LocalDateTime.now()
                )
        );
    }

    // ===================== GET USER BY ID =====================

    @Operation(
            summary = "Get user by ID",
            description = "Fetch user details using user ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<APIResponse<User>> getUserById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new APIResponse<>(
                        true,
                        "User fetched",
                        authService.getUserById(id),
                        LocalDateTime.now()
                )
        );
    }

    // ===================== UPDATE USER =====================

    @Operation(
            summary = "Update user",
            description = "Updates user details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<APIResponse<User>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(
                new APIResponse<>(
                        true,
                        "User updated",
                        authService.updateUser(id, request),
                        LocalDateTime.now()
                )
        );
    }

    // ===================== DELETE USER =====================

    @Operation(
            summary = "Delete user",
            description = "Deletes user by ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<APIResponse<String>> deleteUser(
            @PathVariable Long id
    ) {
        authService.deleteUser(id);

        return ResponseEntity.ok(
                new APIResponse<>(
                        true,
                        "User deleted successfully",
                        "Deleted",
                        LocalDateTime.now()
                )
        );
    }
}

