package com.example.issuetracker.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

    private Long id;
    private String fileName;
    private String fileType;
    private long fileSize;
    private LocalDateTime uploadedAt;
}

