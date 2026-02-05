package com.example.issuetracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CommentResponse {
    private Long id;
    private String content;
    private String commentedBy;
    private LocalDateTime createdAt;
}
