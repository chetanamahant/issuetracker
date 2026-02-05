package com.example.issuetracker.controller;

import com.example.issuetracker.dto.request.CommentRequest;
import com.example.issuetracker.dto.response.APIResponse;
import com.example.issuetracker.dto.response.CommentResponse;
import com.example.issuetracker.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor

@Tag(
        name = "Comment APIs",
        description = "APIs for adding, updating, fetching and deleting comments"
)
public class CommentController {

    private final CommentService commentService;

    // ✅ ADD COMMENT
    @Operation(
            summary = "Add a new comment",
            description = "Adds a comment to an issue by a specific user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER','MANAGER')")
    @PostMapping
    public APIResponse<CommentResponse> addComment(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Comment request body",
                    required = true
            )
            @RequestBody CommentRequest request,

            @Parameter(
                    description = "Username of the logged-in user",
                    example = "chirag_mahant",
                    required = true
            )
            @RequestParam String username
    ) {
        return new APIResponse<>(
                true,
                "Comment added",
                commentService.addComment(request, username),
                LocalDateTime.now()
        );
    }

    // ✅ GET COMMENTS BY ISSUE
    @Operation(
            summary = "Get comments by issue",
            description = "Fetch all comments related to an issue ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Issue not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DEVELOPER')")
    @GetMapping("/issue/{issueId}")
    public APIResponse<List<CommentResponse>> getComments(
            @Parameter(
                    description = "Issue ID",
                    example = "101",
                    required = true
            )
            @PathVariable Long issueId
    ) {
        return new APIResponse<>(
                true,
                "Comments fetched",
                commentService.getCommentsByIssue(issueId),
                LocalDateTime.now()
        );
    }

    // ✅ UPDATE COMMENT
    @Operation(
            summary = "Update comment",
            description = "Update an existing comment by comment ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized user")
    })
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER','MANAGER')")
    @PutMapping("/{commentId}")
    public APIResponse<CommentResponse> updateComment(
            @Parameter(
                    description = "Comment ID",
                    example = "55",
                    required = true
            )
            @PathVariable Long commentId,

            @Valid
            @RequestBody CommentRequest request,

            @Parameter(
                    description = "Username of the user updating the comment",
                    example = "chirag_mahant",
                    required = true
            )
            @RequestParam String username
    ) {
        return new APIResponse<>(
                true,
                "Comment updated",
                commentService.updateComment(commentId, request, username),
                LocalDateTime.now()
        );
    }

    // ✅ DELETE COMMENT
    @Operation(
            summary = "Delete comment",
            description = "Deletes a comment by comment ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized user")
    })
    @PreAuthorize("hasAnyRole('DEVELOPER','MANAGER','ADMIN')")
    @DeleteMapping("/{commentId}")
    public APIResponse<String> deleteComment(
            @Parameter(
                    description = "Comment ID",
                    example = "55",
                    required = true
            )
            @PathVariable Long commentId,

            @Parameter(
                    description = "Username of the user deleting the comment",
                    example = "chirag_mahant",
                    required = true
            )
            @RequestParam String username
    ) {
        commentService.deleteComment(commentId, username);

        return new APIResponse<>(
                true,
                "Comment deleted successfully",
                null,
                LocalDateTime.now()
        );
    }
}

