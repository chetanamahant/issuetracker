package com.example.issuetracker.controller;

import com.example.issuetracker.dto.request.ProjectRequest;
import com.example.issuetracker.dto.response.APIResponse;
import com.example.issuetracker.dto.response.ProjectResponse;
import com.example.issuetracker.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(
        name = "Project APIs",
        description = "APIs for creating and fetching projects"
)
public class ProjectController {

    private final ProjectService projectService;

    // ✅ CREATE PROJECT
    @Operation(
            summary = "Create project",
            description = "Creates a new project"
    )
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public APIResponse<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request) {

        return new APIResponse<>(
                true,
                "Project created",
                projectService.createProject(request),
                LocalDateTime.now()
        );
    }

    // ✅ GET ALL PROJECTS
    @Operation(
            summary = "Get all projects",
            description = "Fetch all available projects"
    )
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DEVELOPER')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projects fetched successfully")
    })
    @GetMapping
    public APIResponse<List<ProjectResponse>> getAllProjects() {

        return new APIResponse<>(
                true,
                "Projects fetched",
                projectService.getAllProjects(),
                LocalDateTime.now()
        );
    }
}

