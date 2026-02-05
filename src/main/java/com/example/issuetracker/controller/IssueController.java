package com.example.issuetracker.controller;

import com.example.issuetracker.dto.request.*;
import com.example.issuetracker.dto.response.APIResponse;
import com.example.issuetracker.dto.response.IssueResponse;
import com.example.issuetracker.service.IssueService;
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
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@Tag(
        name = "Issue APIs",
        description = "APIs for creating, updating, assigning, filtering and deleting issues"
)
public class IssueController {

    private final IssueService issueService;

    // ✅ CREATE ISSUE
    @Operation(
            summary = "Create issue",
            description = "Creates a new issue"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issue created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public APIResponse<IssueResponse> createIssue(
            @RequestBody IssueRequest request) {

        return new APIResponse<>(
                true,
                "Issue created",
                issueService.createIssue(request),
                LocalDateTime.now()
        );
    }

    // ✅ UPDATE ISSUE
    @Operation(
            summary = "Update issue",
            description = "Updates an existing issue using issue ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issue updated successfully"),
            @ApiResponse(responseCode = "404", description = "Issue not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public APIResponse<IssueResponse> updateIssue(
            @PathVariable Long id,
            @RequestBody IssueUpdateRequest request) {

        return new APIResponse<>(
                true,
                "Issue updated",
                issueService.updateIssue(id, request),
                LocalDateTime.now()
        );
    }

    // ✅ GET ALL / FILTER
    @Operation(
            summary = "Filter issues",
            description = "Fetch issues based on filter criteria"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issues fetched successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DEVELOPER')")
    @PostMapping("/filter")
    public APIResponse<List<IssueResponse>> getIssues(
            @RequestBody IssueFilterRequest filter) {

        return new APIResponse<>(
                true,
                "Issues fetched",
                issueService.getIssuesByFilter(filter),
                LocalDateTime.now()
        );
    }

    // ✅ ASSIGN ISSUE
    @Operation(
            summary = "Assign issue",
            description = "Assigns an issue to a user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issue assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Issue or user not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{issueId}/assign/{userId}")
    public APIResponse<IssueResponse> assignIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId) {

        return new APIResponse<>(
                true,
                "Issue assigned",
                issueService.assignIssue(issueId, userId),
                LocalDateTime.now()
        );
    }

    // ✅ DELETE ISSUE
    @Operation(
            summary = "Delete issue",
            description = "Deletes an issue using issue ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Issue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Issue not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public APIResponse<String> deleteIssue(
            @PathVariable Long id) {

        issueService.deleteIssue(id);

        return new APIResponse<>(
                true,
                "Issue deleted successfully",
                "Deleted",
                LocalDateTime.now()
        );
    }
}
