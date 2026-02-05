package com.example.issuetracker.controller;

import com.example.issuetracker.dto.response.APIResponse;
import com.example.issuetracker.dto.response.FileResponse;
import com.example.issuetracker.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor

@Tag(
        name = "File APIs",
        description = "File upload related APIs"
)
public class FileController {

    private final FileService fileService;

    @Operation(
            summary = "Upload file",
            description = "Uploads a file for a specific issue ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or issue ID")
    })
    @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER','MANAGER')")
    @PostMapping(
            value = "/upload/{issueId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public APIResponse<FileResponse> uploadFile(
            @PathVariable Long issueId,
            @RequestParam MultipartFile file) {

        return new APIResponse<>(
                true,
                "File uploaded",
                fileService.uploadFile(issueId, file),
                LocalDateTime.now()
        );
    }
}

