package com.example.issuetracker.dto.response;

import com.example.issuetracker.enums.IssuePriority;
import com.example.issuetracker.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class IssueResponse {

    private Long id;
    private String title;
    private String description;
    private String priority;
    private String status;

    private String projectName;
    private String assignedTo;
    private String createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
