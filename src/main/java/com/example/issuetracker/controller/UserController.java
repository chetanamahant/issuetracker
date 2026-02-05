package com.example.issuetracker.controller;

import com.example.issuetracker.dto.response.APIResponse;
import com.example.issuetracker.dto.response.UserResponse;
import com.example.issuetracker.entity.User;
import com.example.issuetracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
        name = "User APIs",
        description = "APIs for fetching user details"
)
public class UserController {

    private final UserService userService;

    // ✅ GET ALL USERS
    @Operation(
            summary = "Get all users",
            description = "Fetch all users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public APIResponse<List<UserResponse>> getAllUsers() {

        return new APIResponse<>(
                true,
                "Users fetched",
                userService.getAllUsers(),
                LocalDateTime.now()
        );
    }
}
